package de.fokus.fraunhofer.jflow.http;

import de.fokus.fraunhofer.jflow.auth.CookieAuth;
import de.fokus.fraunhofer.jflow.pojo.AuthData;
import de.fokus.fraunhofer.jflow.pojo.Server;
import de.fokus.fraunhofer.jflow.flow.FlowHttpEntityGenerator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HTTPFileUpload {

    private Server httpServer;
    private AuthData authData;

    private static final Logger logger = LoggerFactory.getLogger(HTTPFileUpload.class);

    public void activateAuth(String email,String password,String authPath) {
        this.authData = new AuthData(email,password,authPath);
        this.httpServer.setAuthentication(true);

    }


    public HTTPFileUpload(String ipAddress, int port, boolean authentication, String path){
        this.httpServer = new Server(ipAddress,port,authentication,path);

    }

    private List<Cookie> auth() throws IOException{

        CookieAuth cookieAuth = new CookieAuth(this.httpServer.getAPIUrl()+this.authData.getAuthPath(),
                this.authData.getEmail(),this.authData.getPassword());
        return cookieAuth.auth();

    }

    private HttpContext generateContextWithCookies(List<Cookie> cookies){
        CookieStore cookieStore = new BasicCookieStore();
        for(int i = 0;i<cookies.size();i++){
            cookieStore.addCookie(cookies.get(i));
        }
        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
        return localContext;

    }

    private String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


    private int startUploadFile(String filePath,String apiPath,String targetFileName) throws IOException{
        String apiUrl = this.httpServer.generateAPIUrl(apiPath);
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost post = new HttpPost(apiUrl);

        HttpContext localContext = null;

        if(this.httpServer.isAuthentication()){
            List<Cookie> cookies;
            cookies = this.auth();
            localContext = this.generateContextWithCookies(cookies);

        }


        IFileToHttpEntity entityGenerator = new FlowHttpEntityGenerator();

        entityGenerator.init(filePath, targetFileName);

        int statusCode = 0;

        long startTime = System.currentTimeMillis();

        while(entityGenerator.hasNext()){

            statusCode = this.uploadChunk(entityGenerator,post,client,localContext);
            if(HttpStatus.SC_OK != statusCode){
                return statusCode;
            }

        }
        long endTime = System.currentTimeMillis();
        logger.info("File Total Upload Time: " + (endTime - startTime) + " milliseconds");
        return statusCode;

    }

    public int uploadFile(String filePath,String apiPath) throws IOException{

        Path path = Paths.get(filePath);
        String targetFileName = path.getFileName().toString();

        return this.startUploadFile(filePath,apiPath,targetFileName);
    }


    public int uploadFile(String filePath,String apiPath, String targetFileName) throws IOException{

        return this.startUploadFile(filePath,apiPath,targetFileName);
    }

    private int uploadChunk(IFileToHttpEntity entityGenerator, HttpPost post, HttpClient client,
                            HttpContext localContext) throws IOException {
        int statusCode;
        HttpEntity entity = entityGenerator.next();
        post.setEntity(entity);
        logger.info(post.toString());

        HttpResponse response;
        if(this.httpServer.isAuthentication()){
            response = client.execute(post,localContext);
        }
        else {
            response = client.execute(post);
        }

        StatusLine statusLine = response.getStatusLine();

        statusCode = statusLine.getStatusCode();

        //logger.info("API Response ==> " + response.toString());

        InputStream responseContent = response.getEntity().getContent();

        logger.info("API Response ==> "+convertStreamToString(responseContent));

        return statusCode;


    }

}
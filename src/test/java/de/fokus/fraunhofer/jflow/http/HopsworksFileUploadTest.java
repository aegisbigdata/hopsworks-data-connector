package de.fokus.fraunhofer.jflow.http;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class HopsworksFileUploadTest {

    HTTPFileUpload httpFileUpload = null;
    String projectId;
    String folder;

    @Before
    public void setUp(){
        String authPath = "/auth/login";
        String email = "admin@kth.se";
        String password = "admin";

        String ipAddress = "http://bbc6.sics.se"; //test server
        int port = 8080;
        String path = "/hopsworks-api/api";

        projectId = "1027";
        folder = "upload/fokus_mpo_data";

        httpFileUpload = new HTTPFileUpload(ipAddress,port,true,path);
        httpFileUpload.activateAuth(email,password,authPath);

    }

    @Test
    public void simpleFileUploadTest() {

        //String uploadPath = "/project/{id}/dataset/{fileName}";
        String testProjectUploadPath = "/project/"+projectId+"/dataset/"+folder;

        int statusCode = 0;
        try {
            //file < 1 MB
            statusCode = httpFileUpload.uploadFile("sample-files/obama.jpg",testProjectUploadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals( "statusCode should be 200 OK",statusCode,HttpStatus.SC_OK  );
    }
    @Test
    public void mediumFileUploadTest()
    {

        //String uploadPath = "/project/{id}/dataset/{fileName}";
        String testProjectUploadPath = "/project/"+projectId+"/dataset/"+folder;

        int statusCode = 0;
        try {
            //file  ~ 10 MB
            statusCode = httpFileUpload.uploadFile("sample-files/pizigani.jpg",testProjectUploadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals( "statusCode should be 200 OK",statusCode, HttpStatus.SC_OK  );
    }

}

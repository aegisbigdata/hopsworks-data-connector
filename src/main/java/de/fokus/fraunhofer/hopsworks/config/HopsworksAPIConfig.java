package de.fokus.fraunhofer.hopsworks.config;

import java.net.MalformedURLException;
import java.net.URL;

public class HopsworksAPIConfig {

    private String userName;
    private String password;
    private String apiUrl;

    private URL url;

    private final String DEFAULT_PATH_FILE_UPLOAD = "/project/{id}/dataset/{fileName}";
    private final String DEFAULT_PATH_LOGIN = "/auth/login";


    private String pathLogin;
    private String pathFileUpload;

    public HopsworksAPIConfig(String userName,String password, String apiUrl){
        this.userName = userName;
        this.password = password;
        this.apiUrl = apiUrl;

        try {
            this.url = new URL(this.apiUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.pathLogin = this.DEFAULT_PATH_LOGIN;
        this.pathFileUpload = this.DEFAULT_PATH_FILE_UPLOAD;
    }

    public String getPathLogin() {
        return pathLogin;
    }

    public String getPathFileUpload() {
        return pathFileUpload;
    }

    public void setPathFileUpload(String pathFileUpload) {
        this.pathFileUpload = pathFileUpload;
    }

    public void setPathLogin(String pathLogin) {
        this.pathLogin = pathLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public int getPort(){
        int port = this.url.getPort();
        if(port == -1){
            return 80; //no specific port in the url means standard port 80
        }
        return port;
    }
    public String getHost(){
        return this.url.getHost();
    }
    public String getPath(){
        return this.url.getPath();
    }
    public String getProtocol() {
        return this.url.getProtocol();
    }

}

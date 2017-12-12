package de.fokus.fraunhofer.hopswork.config;

import java.util.Map;

/*

Bean for config.yml File

 */

public class Config {


    public static final String SERVER_URL ="url";
    public static final String SERVER_PORT ="port";
    public static final String SERVER_PATH ="path";

    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";

    public static final String API_AUTH = "auth";
    public static final String API_FILE_UPLOAD = "fileUpload";



    private Map<String,String> server;
    private Map<String,String> user;
    private Map<String,String> api;

    public void setServer(Map<String, String> server) {
        this.server = server;
    }

    public void setUser(Map<String, String> user) {
        this.user = user;
    }

    public Map<String, String> getServer() {
        return server;
    }

    public Map<String, String> getUser() {
        return user;
    }

    public Map<String, String> getApi() {
        return api;
    }

    public void setApi(Map<String, String> api) {
        this.api = api;
    }
}

package de.fokus.fraunhofer.jflow.pojo;

public class Server {

    private String ipAddress;
    private int port;
    boolean authentication;
    String path;

    public Server(String ipAddress,int port,boolean authentication,String path){

        this.ipAddress = ipAddress;
        this.port = port;
        this.authentication = authentication;
        this.path = path;

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAPIUrl(){
        return this.ipAddress + ":"+this.port+this.path;
    }

    public String generateAPIUrl(String apiPath){
        return this.getAPIUrl() + apiPath;
    }

}

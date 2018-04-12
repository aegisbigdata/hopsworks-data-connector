package de.fokus.fraunhofer.hopsworks.action;

import de.fokus.fraunhofer.hopsworks.config.Config;
import de.fokus.fraunhofer.hopsworks.config.HopsworksAPIConfig;
import de.fokus.fraunhofer.jflow.http.HTTPFileUpload;
import org.apache.http.HttpStatus;

public class FileUploadAction implements IHopsworksAction {

    private HopsworksAPIConfig hopsworksAPIConfig;
    private String hopsworkProjectId;
    private String hopsworkFolder;
    private String filePath;

    private HTTPFileUpload httpFileUpload = null;

    public FileUploadAction(HopsworksAPIConfig hopsworksAPIConfig, String hopsworkProjectId, String hopsworkFolder, String filePath){

        this.hopsworksAPIConfig = hopsworksAPIConfig;
        this.hopsworkProjectId = hopsworkProjectId;
        this.hopsworkFolder = hopsworkFolder;
        this.filePath = filePath;

        this.init();

    }

    private void init(){
        String url = this.hopsworksAPIConfig.getApiUrl();

        String authPath = this.hopsworksAPIConfig.getPathLogin();
        String userName = this.hopsworksAPIConfig.getUserName();
        String password = this.hopsworksAPIConfig.getPassword();

        httpFileUpload = new HTTPFileUpload(this.hopsworksAPIConfig.getProtocol()+"://"+this.hopsworksAPIConfig.getHost(),this.hopsworksAPIConfig.getPort(),
                true,this.hopsworksAPIConfig.getPath());
        httpFileUpload.activateAuth(userName,password,authPath);

    }

    private String generateUploadPath(){
        String uploadPath = this.hopsworksAPIConfig.getPathFileUpload();
        uploadPath = uploadPath.replace("{id}",this.hopsworkProjectId);
        uploadPath = uploadPath.replace("{fileName}",this.hopsworkFolder);
        return uploadPath;
    }


    @Override
    public void execute() throws Exception {

        int statusCode;
        String completeUploadPath = generateUploadPath();
        statusCode = httpFileUpload.uploadFile(this.filePath,completeUploadPath);

        if(statusCode != HttpStatus.SC_OK){
            throw new Exception("HTTP File Upload not successful");
        }

    }
}

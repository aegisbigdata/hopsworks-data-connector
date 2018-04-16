package de.fokus.fraunhofer.hopsworks.action;

import de.fokus.fraunhofer.hopsworks.config.HopsworksAPIConfig;
import de.fokus.fraunhofer.jflow.http.HTTPFileUpload;
import org.apache.http.HttpStatus;

public class FileUploadAction implements IHopsworksAction {

    private HopsworksAPIConfig hopsworksAPIConfig;
    private String hopsworkProjectId;
    private String hopsworkFolder;
    private String filePath;
    private String targetFileName = null;

    private HTTPFileUpload httpFileUpload = null;

    public FileUploadAction(HopsworksAPIConfig hopsworksAPIConfig, String hopsworkProjectId, String hopsworkFolder, String filePath,
    String targetFileName){

        this.targetFileName = targetFileName; //optional parameter
        this.init(hopsworksAPIConfig,hopsworkProjectId,hopsworkFolder,filePath);

    }


    public FileUploadAction(HopsworksAPIConfig hopsworksAPIConfig, String hopsworkProjectId, String hopsworkFolder, String filePath){

        this.init(hopsworksAPIConfig,hopsworkProjectId,hopsworkFolder,filePath);

    }

    private void init(HopsworksAPIConfig hopsworksAPIConfig, String hopsworkProjectId, String hopsworkFolder, String filePath){

        this.hopsworksAPIConfig = hopsworksAPIConfig;
        this.hopsworkProjectId = hopsworkProjectId;
        this.hopsworkFolder = hopsworkFolder;
        this.filePath = filePath;

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
        if(this.targetFileName == null) {
            statusCode = httpFileUpload.uploadFile(this.filePath, completeUploadPath);
        }
        else
        {
            statusCode = httpFileUpload.uploadFile(this.filePath, completeUploadPath,this.targetFileName);
        }

        if(statusCode != HttpStatus.SC_OK){
            throw new Exception("HTTP File Upload not successful");
        }

    }
}

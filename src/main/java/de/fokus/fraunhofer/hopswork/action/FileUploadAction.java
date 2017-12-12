package de.fokus.fraunhofer.hopswork.action;

import de.fokus.fraunhofer.hopswork.config.Config;
import de.fokus.fraunhofer.jflow.http.HTTPFileUpload;
import org.apache.http.HttpStatus;

import java.io.IOException;

public class FileUploadAction implements IHopsworkAction {

    private Config config;
    private String hopsworkProjectId;
    private String hopsworkFolder;
    private String filePath;

    private HTTPFileUpload httpFileUpload = null;


    public FileUploadAction(Config hopsworkConfig, String hopsworkProjectId,String hopsworkFolder,String filePath){

        this.config = hopsworkConfig;
        this.hopsworkProjectId = hopsworkProjectId;
        this.hopsworkFolder = hopsworkFolder;
        this.filePath = filePath;

        this.init();

    }

    private void init(){
        String url = this.config.getServer().get(Config.SERVER_URL);
        int port = Integer.parseInt(this.config.getServer().get(Config.SERVER_PORT));
        String path = this.config.getServer().get(Config.SERVER_PATH);

        String authPath = this.config.getApi().get(Config.API_AUTH);
        String email = this.config.getUser().get(Config.USER_EMAIL);
        String password = this.config.getUser().get(Config.USER_PASSWORD);

        httpFileUpload = new HTTPFileUpload(url,port,true,path);
        httpFileUpload.activateAuth(email,password,authPath);

    }

    private String generateUploadPath(){
        String uploadPath = this.config.getApi().get(Config.API_FILE_UPLOAD);
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

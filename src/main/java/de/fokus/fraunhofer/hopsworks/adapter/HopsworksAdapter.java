package de.fokus.fraunhofer.hopsworks.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.fokus.fraunhofer.hopsworks.action.FileUploadAction;
import de.fokus.fraunhofer.hopsworks.action.IHopsworksAction;
import de.fokus.fraunhofer.hopsworks.config.Config;
import de.fokus.fraunhofer.hopsworks.config.ConfigReader;
import de.fokus.fraunhofer.hopsworks.config.HopsworksAPIConfig;

import java.io.File;
import java.io.IOException;

public class HopsworksAdapter {


   private Config config;
   private HopsworksAPIConfig hopsworkAPIConfig;

   public void setConfig(Config config) {
      this.config = config;
   }

   public Config getConfig() {
      return config;
   }

   public HopsworksAdapter(){
      try {
         this.config = new ConfigReader().read();
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public HopsworksAdapter(String configFilePath){
      try {
         this.config = new ConfigReader().read(configFilePath);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public HopsworksAdapter(String apiUrl,String userName, String password) {

      this.hopsworkAPIConfig = new HopsworksAPIConfig(userName,password,apiUrl);
   }


   public void actionUploadFile(String hopsworkProjectId,String hopsworkFolder,String filePath){

      IHopsworksAction uploadAction = new FileUploadAction(this.config,hopsworkProjectId,hopsworkFolder,filePath);

      try {
         uploadAction.execute();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}

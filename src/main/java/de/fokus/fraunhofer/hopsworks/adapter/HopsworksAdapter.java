package de.fokus.fraunhofer.hopsworks.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.fokus.fraunhofer.hopsworks.action.FileUploadAction;
import de.fokus.fraunhofer.hopsworks.action.IHopsworksAction;
import de.fokus.fraunhofer.hopsworks.config.Config;
import de.fokus.fraunhofer.hopsworks.config.ConfigReader;

import java.io.File;
import java.io.IOException;

public class HopsworksAdapter {



   private Config config;

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

   public void actionUploadFile(String hopsworkProjectId,String hopsworkFolder,String filePath){

      IHopsworksAction uploadAction = new FileUploadAction(this.config,hopsworkProjectId,hopsworkFolder,filePath);

      try {
         uploadAction.execute();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}

package de.fokus.fraunhofer.hopswork.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.fokus.fraunhofer.hopswork.action.FileUploadAction;
import de.fokus.fraunhofer.hopswork.action.IHopsworkAction;
import de.fokus.fraunhofer.hopswork.config.Config;

import java.io.File;

public class HopsworkAdapter {

   final String CONFIG_FILE = "config.yml";

   private Config config;

   public void setConfig(Config config) {
      this.config = config;
   }

   public Config getConfig() {
      return config;
   }

   public HopsworkAdapter(){
      this.loadConfig();

   }

   public void actionUploadFile(String hopsworkProjectId,String hopsworkFolder,String filePath){

      IHopsworkAction uploadAction = new FileUploadAction(this.config,hopsworkProjectId,hopsworkFolder,filePath);

      try {
         uploadAction.execute();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private void loadConfig(){
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

      try {

          this.config = mapper.readValue(new File("config.yml"), Config.class);


      } catch (Exception e) {

         e.printStackTrace();

      }

   }
}

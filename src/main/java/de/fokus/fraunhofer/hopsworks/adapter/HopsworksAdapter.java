package de.fokus.fraunhofer.hopsworks.adapter;

import de.fokus.fraunhofer.hopsworks.action.FileUploadAction;
import de.fokus.fraunhofer.hopsworks.action.IHopsworksAction;
import de.fokus.fraunhofer.hopsworks.config.HopsworksAPIConfig;

public class HopsworksAdapter {

   private HopsworksAPIConfig hopsworkAPIConfig;

   public HopsworksAPIConfig getHopsworkAPIConfig() {
      return hopsworkAPIConfig;
   }

   public void setHopsworkAPIConfig(HopsworksAPIConfig hopsworkAPIConfig) {
      this.hopsworkAPIConfig = hopsworkAPIConfig;
   }

   public HopsworksAdapter(String userName, String password,String apiUrl) {

      this.hopsworkAPIConfig = new HopsworksAPIConfig(userName,password,apiUrl);
   }


   public void actionUploadFile(String hopsworkProjectId,String hopsworkFolder,String filePath){

      IHopsworksAction uploadAction = new FileUploadAction(this.hopsworkAPIConfig, hopsworkProjectId,hopsworkFolder,filePath);

      try {
         uploadAction.execute();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}

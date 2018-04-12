package de.fokus.fraunhofer.hopsworks.adapter;

import org.junit.Before;
import org.junit.Test;

public class HopsworksAdapterTest {

    HopsworksAdapter hopsworksAdapter;
    String projectId;
    String folder;

    @Before
    public void setUp(){

        String url = "http://bbc6.sics.se:8080/hopsworks-api/api";  //test server
        String email = "admin@kth.se";
        String password = "admin";

        hopsworksAdapter = new HopsworksAdapter(email,password,url);

         projectId = "1027";
         folder = "upload/fokus_mpo_data";
    }

    @Test
    public void simpleFileUploadTest() {

        // sample path /project/1/dataset/upload/test_mpo"
        String filePath = "sample-files/obama.jpg";

        hopsworksAdapter.actionUploadFile(projectId,folder,filePath);

        //TODO automatic file upload check

    }
    @Test
    public void mediumFileUploadTest() {

        String filePath = "sample-files/pizigani.jpg";

        String projectId = "1027";
        String folder = "upload/fokus_mpo_data";

        hopsworksAdapter.actionUploadFile(projectId,folder,filePath);

        //TODO automatic file upload check

    }
}

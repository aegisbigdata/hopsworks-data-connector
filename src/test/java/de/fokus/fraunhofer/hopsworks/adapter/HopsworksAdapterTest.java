package de.fokus.fraunhofer.hopsworks.adapter;

import de.fokus.fraunhofer.hopsworks.config.Config;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HopsworksAdapterTest {

    @Before
    public void setUp(){


    }

    @Test
    public void readConfigFileTest() {

        HopsworksAdapter hopsworksAdapter = new HopsworksAdapter();
        Config config = hopsworksAdapter.getConfig();
        String url = config.getServer().get("url");

        assertTrue("should load config file and basic check url ",url.contains("http://"));

    }
    @Test
    public void simpleFileUploadTest() {

        // sample path /project/1/dataset/upload/test_mpo"
        String filePath = "sample-files/obama.jpg";

        String projectId = "1027";
        String folder = "upload/fokus_mpo_data";

        HopsworksAdapter hopsworksAdapter = new HopsworksAdapter();
        hopsworksAdapter.actionUploadFile(projectId,folder,filePath);

        //TODO automatic file upload check

    }
    @Test
    public void mediumFileUploadTest() {

        String filePath = "sample-files/pizigani.jpg";

        String projectId = "1027";
        String folder = "upload/fokus_mpo_data";

        HopsworksAdapter hopsworksAdapter = new HopsworksAdapter();
        hopsworksAdapter.actionUploadFile(projectId,folder,filePath);

        //TODO automatic file upload check

    }
}

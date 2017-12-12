package de.fokus.fraunhofer.hopswork.adapter;

import de.fokus.fraunhofer.hopswork.config.Config;
import de.fokus.fraunhofer.jflow.auth.CookieAuth;
import org.apache.http.cookie.Cookie;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class HopsworkAdapterTest {

    @Before
    public void setUp(){


    }

    @Test
    public void readConfigFileTest() {

        HopsworkAdapter hopsworkAdapter = new HopsworkAdapter();
        Config config = hopsworkAdapter.getConfig();
        String url = config.getServer().get("url");

        assertTrue("should load config file and basic check url ",url.contains("http://"));

    }
    @Test
    public void simpleFileUploadTest() {

        // sample path /project/1/dataset/upload/test_mpo"
        String filePath = "sample-files/obama.jpg";

        String projectId = "1";
        String folder = "upload/test_mpo";

        HopsworkAdapter hopsworkAdapter = new HopsworkAdapter();
        hopsworkAdapter.actionUploadFile(projectId,folder,filePath);

        //TODO automatic file upload check

    }
    @Test
    public void mediumFileUploadTest() {

        String filePath = "sample-files/pizigani.jpg";

        String projectId = "1";
        String folder = "upload/test_mpo";

        HopsworkAdapter hopsworkAdapter = new HopsworkAdapter();
        hopsworkAdapter.actionUploadFile(projectId,folder,filePath);

        //TODO automatic file upload check

    }
}

package de.fokus.fraunhofer.jflow.http;


import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;


public class NodeBackendFileUploadTest {

    HTTPFileUpload httpFileUpload = null;

    @Before
    public void setUp(){
        httpFileUpload = new HTTPFileUpload("http://localhost",3000,false,"/upload");

    }

    @Test
    public void simpleFileUploadTest() {

     /*   int statusCode = 0;
        try {
            //file < 1 MB
            statusCode = httpFileUpload.uploadFile("sample-files/obama.jpg","");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue( "statusCode should be 200 OK",statusCode == HttpStatus.SC_OK  );
        */
    }
    @Test
    public void mediumFileUploadTest()
    {
        /*
        int statusCode = 0;
        try {
            //file  ~ 10 MB
            statusCode = httpFileUpload.uploadFile("sample-files/pizigani.jpg","");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue( "statusCode should be 200 OK",statusCode == HttpStatus.SC_OK  );
        */
    }



}

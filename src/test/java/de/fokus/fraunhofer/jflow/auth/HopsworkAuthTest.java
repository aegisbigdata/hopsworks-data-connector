package de.fokus.fraunhofer.jflow.auth;

import org.apache.http.cookie.Cookie;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class HopsworkAuthTest {
    @Before
    public void setUp(){


    }

    @Test
    public void authTest() {

        String authPath = "/auth/login";
        String hopsworkServer = "http://bbc6.sics.se:8080/hopsworks-api/api";
        String user = "admin@kth.se";
        String password = "admin";

        CookieAuth cookieAuth = new CookieAuth(hopsworkServer+authPath,user,password);
        try {
            List<Cookie> cookies =  cookieAuth.auth();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue("should authenticate and return cookies",true);

    }

}

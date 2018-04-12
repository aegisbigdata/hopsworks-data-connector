package de.fokus.fraunhofer.jflow.auth;

import de.fokus.fraunhofer.hopsworks.config.HopsworksAPIConfig;
import org.apache.http.cookie.Cookie;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class HopsworksAuthTest {

   HopsworksAPIConfig hopsworksAPIConfig;

    @Before
    public void setUp(){

        String url = "http://bbc6.sics.se:8080/hopsworks-api/api";  //test server
        String email = "admin@kth.se";
        String password = "admin";

        this.hopsworksAPIConfig = new HopsworksAPIConfig(email,password,url);

    }

    @Test
    public void authTest() {

        String authPath = this.hopsworksAPIConfig.getPathLogin();

        final String SESSION_NAME = "SESSION";
        final int SESSION_POS = 1;

        final String JSESSIONIDSSO_NAME = "JSESSIONIDSSO";
        final int JSESSIONIDSSO_POS = 0;


        CookieAuth cookieAuth = new CookieAuth(this.hopsworksAPIConfig.getApiUrl()+authPath,
                this.hopsworksAPIConfig.getUserName(),this.hopsworksAPIConfig.getPassword());
        List<Cookie> cookies = null;
        try {
            cookies =  cookieAuth.auth();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue("should authenticate and return cookies",cookies.get(JSESSIONIDSSO_POS).getName()
                .compareTo(JSESSIONIDSSO_NAME) == 0 && cookies.get(SESSION_POS).getName().compareTo(SESSION_NAME) == 0);

    }

}

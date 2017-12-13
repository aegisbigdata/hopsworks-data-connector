package de.fokus.fraunhofer.jflow.auth;

import de.fokus.fraunhofer.hopsworks.config.Config;
import de.fokus.fraunhofer.hopsworks.config.ConfigReader;
import org.apache.http.cookie.Cookie;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class HopsworksAuthTest {

    Config config;

    @Before
    public void setUp(){

        try {
            config = new ConfigReader().read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void authTest() {

        String authPath = config.getApi().get(Config.API_AUTH);

        Map<String,String> server = config.getServer();
        String hopsworkServer = server.get(Config.SERVER_URL)+ ":" + server.get(Config.SERVER_PORT) + server.get(Config.SERVER_PATH);

        String user = config.getUser().get(Config.USER_EMAIL);
        String password = config.getUser().get(Config.USER_PASSWORD);

        final String SESSION_NAME = "SESSION";
        final int SESSION_POS = 1;

        final String JSESSIONIDSSO_NAME = "JSESSIONIDSSO";
        final int JSESSIONIDSSO_POS = 0;


        CookieAuth cookieAuth = new CookieAuth(hopsworkServer+authPath,user,password);
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

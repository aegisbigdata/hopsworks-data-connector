package de.fokus.fraunhofer.hopsworks.config;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HopsworksAPIConfigTest {


    @Test
    public void getPortTest() {

        HopsworksAPIConfig config = new HopsworksAPIConfig("test@test.de","1234","http://www.test.de:8080/api/hopsworks");
        assertEquals("should return the port",config.getPort(),8080);

    }
    @Test
    public void getStandardPortTest() {

        HopsworksAPIConfig config = new HopsworksAPIConfig("test@test.de","1234","http://www.test.de/api/hopsworks");
        assertEquals("should return the port",config.getPort(),80);

    }
    @Test
    public void getHostTest() {

        HopsworksAPIConfig config = new HopsworksAPIConfig("test@test.de","1234","http://www.test.de:8080/api/hopsworks");
        System.out.println(config.getHost());
        assertEquals("should return the the host",config.getHost(),"www.test.de");

    }
    @Test
    public void getPathTest() {

        HopsworksAPIConfig config = new HopsworksAPIConfig("test@test.de","1234","http://www.test.de:8080/api/hopsworks");
        assertEquals("should return the the path",config.getPath(),"/api/hopsworks");

    }
    @Test
    public void getProtocolTest() {

        HopsworksAPIConfig config = new HopsworksAPIConfig("test@test.de","1234","http://www.test.de:8080/api/hopsworks");
        assertEquals("should return the the path",config.getProtocol(),"http");

    }



}

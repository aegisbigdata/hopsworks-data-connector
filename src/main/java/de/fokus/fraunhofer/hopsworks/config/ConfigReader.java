package de.fokus.fraunhofer.hopsworks.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ConfigReader {

    final String DEFAULT_CONFIG_FILE_PATH = "config.yml";

    public Config read(String configFilePath) throws IOException {
        return this.readConfigFile(configFilePath);

    }

    public Config read() throws IOException {
        return this.readConfigFile(DEFAULT_CONFIG_FILE_PATH);

    }

    private Config readConfigFile(String configFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        Config config = mapper.readValue(new File(configFilePath), Config.class);

        return config;

    }
}

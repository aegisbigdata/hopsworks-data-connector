package de.fokus.fraunhofer.hopsworks.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ConfigReader {

    final String CONFIG_FILE = "config.yml";

    public Config read() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        Config config = mapper.readValue(new File(CONFIG_FILE), Config.class);

        return config;

    }
}

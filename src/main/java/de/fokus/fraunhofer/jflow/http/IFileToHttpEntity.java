package de.fokus.fraunhofer.jflow.http;

import org.apache.http.HttpEntity;

import java.io.IOException;

public interface IFileToHttpEntity {

    void init(String filePath,String targetFileName) throws IOException;
    HttpEntity next() throws IOException;
    boolean hasNext();
}

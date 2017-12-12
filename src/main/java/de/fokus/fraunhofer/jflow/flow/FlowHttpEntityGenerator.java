package de.fokus.fraunhofer.jflow.flow;

import de.fokus.fraunhofer.jflow.http.IFileToHttpEntity;
import de.fokus.fraunhofer.jflow.io.FileReader;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;


public class FlowHttpEntityGenerator implements IFileToHttpEntity {

    private FlowParameter parameter;
    FileReader fileReader;

    private final long FLOW_STANDARD_CHUNK_SIZE = 1024*1024;

    private long sizeLastChunk;

    private long calculateSizeLastChunk(long chunkSize,long fileSize){



        long lastChunk = fileSize % chunkSize;
       /* if(lastChunk == 0){
            lastChunk = chunkSize;
        }*/
        return lastChunk;
    }

    private long calculateTotalChunks(long chunkSize,long fileSize){

        long chunks = fileSize/chunkSize;
        if(chunks == 0){
            chunks = 1;
        }
        return chunks;

    }

    public void init(String filePath) throws IOException {

        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();

        System.out.println("[Info] FileName: "+fileName);

        fileReader = new FileReader();
        long fileSize = fileReader.open(filePath);

        //Flow Parameter
        parameter = new FlowParameter();
        parameter.setFlowChunkNumber(1); //chunk starts with 1 not 0
        parameter.setFlowChunkSize(this.FLOW_STANDARD_CHUNK_SIZE);
        parameter.setFlowCurrentChunkSize(this.FLOW_STANDARD_CHUNK_SIZE);
        parameter.setFlowTotalSize(fileSize);

        String flowIdentifier = fileSize + "-"+fileName.replaceAll("[^0-9A-Za-z_-]", "");

        parameter.setFlowIdentifier(flowIdentifier);
        parameter.setFlowFilename(fileName);
        parameter.setFlowRelativePath(fileName);

        long totalChunks = calculateTotalChunks(this.FLOW_STANDARD_CHUNK_SIZE,fileSize);
        System.out.println("[Info] Total File Chunks:"+totalChunks);
        this.sizeLastChunk = this.calculateSizeLastChunk(this.FLOW_STANDARD_CHUNK_SIZE,fileSize);
        System.out.println("[Info] Last Chunk Size:"+this.sizeLastChunk);
        parameter.setFlowTotalChunks(totalChunks);

    }

    private void addFlowParameter(MultipartEntityBuilder builder){

        builder.addTextBody("templateId", "-1" + "", ContentType.TEXT_PLAIN);
        builder.addTextBody("flowChunkNumber", parameter.getFlowChunkNumber() + "", ContentType.TEXT_PLAIN);
        builder.addTextBody("flowChunkSize", parameter.getFlowChunkSize() + "", ContentType.TEXT_PLAIN);
        builder.addTextBody("flowCurrentChunkSize", parameter.getFlowCurrentChunkSize() + "", ContentType.TEXT_PLAIN);
        builder.addTextBody("flowTotalSize", parameter.getFlowTotalSize() + "", ContentType.TEXT_PLAIN);
        builder.addTextBody("flowIdentifier", parameter.getFlowIdentifier() + "", ContentType.TEXT_PLAIN);
        builder.addTextBody("flowFilename", parameter.getFlowFilename() + "", ContentType.TEXT_PLAIN);
        builder.addTextBody("flowRelativePath", parameter.getFlowRelativePath() + "", ContentType.TEXT_PLAIN);
        builder.addTextBody("flowTotalChunks", parameter.getFlowTotalChunks() + "", ContentType.TEXT_PLAIN);

    }

    private String toStringHttpEntity(HttpEntity entity){

        ByteArrayOutputStream out = new ByteArrayOutputStream((int) entity.getContentLength());

        String entityAsString = "";
        try {
            entity.writeTo(out);
            entityAsString = out.toString();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


      return entityAsString;


    }

    private void increaseChunkNumber(){
        parameter.setFlowChunkNumber(parameter.getFlowChunkNumber()+1);
    }

    public HttpEntity next() throws IOException,NoSuchElementException {

        System.out.println("[Info] File Chunk Number: " + parameter.getFlowChunkNumber());
        if(parameter.getFlowChunkNumber() > parameter.getFlowTotalChunks()) {
            throw new NoSuchElementException();
        }

        int chunkSize = 0;
        chunkSize= (int) parameter.getFlowChunkSize();
        if(parameter.getFlowChunkNumber() == parameter.getFlowTotalChunks()){
            //last chunk
            //in flow.js the last chunk is bigger (surprisingly)
            // example: file size 3.7 mb would result only in three chunks. 1 mb,1 mb and 1.7 mb for the last one
            // implementation should have the same behaviour as the official client
            chunkSize += (int) this.sizeLastChunk;

        }

        if(parameter.getFlowTotalSize() < parameter.getFlowChunkSize()){
            //only one chunk for the complete file
            chunkSize = (int) this.sizeLastChunk;
        }


        byte [] chunk;

        chunk = fileReader.readChunk(chunkSize);
        parameter.setFlowCurrentChunkSize(chunkSize);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        this.addFlowParameter(builder);

        builder.addBinaryBody("file", chunk, ContentType.DEFAULT_BINARY, parameter.getFlowFilename());


        HttpEntity entity = builder.build();

        //print http entity
        //System.out.println(this.toStringHttpEntity(entity));


        this.increaseChunkNumber();
        if(parameter.getFlowChunkNumber() > parameter.getFlowTotalChunks()){
            this.fileReader.close();
        }

        return entity;
    }

    public boolean hasNext() {

        if(parameter.getFlowChunkNumber() <= parameter.getFlowTotalChunks()){
            return true;
        }
        return false;
    }
}

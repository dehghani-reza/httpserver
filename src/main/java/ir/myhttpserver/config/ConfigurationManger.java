package ir.myhttpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ir.myhttpserver.util.Json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManger {

    private static Configuration myCurrentConfiguration;
    private static ConfigurationManger myConfigurationManger;

    private ConfigurationManger() {
    }

    public static ConfigurationManger getInstance(){
        if(myConfigurationManger==null)
            myConfigurationManger = new ConfigurationManger();
        return myConfigurationManger;
    }

    /**
     *
     * user to load configuration file by the path provided
     */
    public void loadConfigurationFile(String filePath){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while ((i = fileReader.read())!=-1){
                sb.append((char) i);
            }
        }catch (IOException e){
            throw new HttpConfigurationException(e);
        }

        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing configuration file.",e);
        }
        try {
            myCurrentConfiguration = Json.fromJson(conf,Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing configuration file. internal",e);
        }
    }

    /**
     * return the current loaded configuration
     */
    public Configuration getCurrentConfiguration(){
        if (myCurrentConfiguration==null){
            throw new HttpConfigurationException("No current configuration set.");
        }
        return myCurrentConfiguration;
    }
}

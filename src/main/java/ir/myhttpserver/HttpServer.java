package ir.myhttpserver;

import ir.myhttpserver.config.Configuration;
import ir.myhttpserver.config.ConfigurationManger;

public class HttpServer {

    public static void main(String[] args) {

        System.out.println("server starting ...");

        ConfigurationManger.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManger.getInstance().getCurrentConfiguration();

        System.out.println("Using port: "+conf.getPort());
        System.out.println("Using webroot: "+conf.getWebRoot() );

        
    }
}

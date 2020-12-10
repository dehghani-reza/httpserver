package ir.myhttpserver;

import ir.myhttpserver.config.Configuration;
import ir.myhttpserver.config.ConfigurationManger;
import ir.myhttpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {

        LOGGER.info("server starting ...");

        ConfigurationManger.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManger.getInstance().getCurrentConfiguration();

        LOGGER.info("Using port: "+conf.getPort());
        LOGGER.info("Using webroot: "+conf.getWebRoot() );

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebRoot());
            serverListenerThread.start();
        }catch (IOException e){
            LOGGER.error("problem with server socket setting.",e);
        }
    }
}

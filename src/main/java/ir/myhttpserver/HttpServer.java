package ir.myhttpserver;

import ir.myhttpserver.config.Configuration;
import ir.myhttpserver.config.ConfigurationManger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static void main(String[] args) {

        System.out.println("server starting ...");

        ConfigurationManger.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManger.getInstance().getCurrentConfiguration();

        System.out.println("Using port: "+conf.getPort());
        System.out.println("Using webroot: "+conf.getWebRoot() );

        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            //TODO we would reading

            //TODO we would writing

            String html = """
                    <!DOCTYPE html>
                        <html>
                            <head>
                                <title>Reza Server</title>
                            </head>
                            <body>
                                <p>Hello world from my simple server!!!</p>
                            </body>
                        </html>
                    """;
            final String CRLF = "\n\r"; //13, 10 ASCII
            String response =
                    "HTTP/1.1 200 OK"+CRLF+ // status line : HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                    "Content-Length: "+html.getBytes().length+CRLF+ //Header
                    CRLF+
                    html+
                    CRLF+CRLF;
            outputStream.write(response.getBytes());
            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

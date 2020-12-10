package ir.myhttpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream  = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

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
                    "HTTP/1.1 200 OK" + CRLF + // status line : HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF + //Header
                            CRLF +
                            html +
                            CRLF + CRLF;
            outputStream.write(response.getBytes());

            try{
                sleep(5000);// add this to show request didnt go to queue of serverSocket after implement this class
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("Connection processing finished.");
        }catch (IOException e){
            LOGGER.error("problem with communication.",e);
        }finally {
            if(inputStream!=null ){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
           if(outputStream!=null){
               try {
                   outputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           if(socket!=null){
               try {
                   socket.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }
    }
}

package ir.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass(){
        httpParser = new HttpParser();
    }
    @Test
    void parseHttpRequest() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateValidGETTestCase());
        } catch (HttpParsingException e) {
            fail(e);
        }
        assertEquals(request.getMethod(),HttpMethod.GET);
    }
    @Test
    void parseHttpRequestBadMethod1() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadTestCaseMethodName1());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(),HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }
    @Test
    void parseHttpRequestBadMethod2() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadTestCaseMethodName2());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(),HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }
    @Test
    void parseHttpRequestInvalidNumberItems1() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadTestCaseRequestLineInvalidNumberItem1());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(),HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }
    @Test
    void parseHttpEmptyRequestLine() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadTestCaseEmptyRequestLine());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(),HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }
    @Test
    void parseHttpRequestLineCRnoLF() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(generateBadTestCaseRequestLineInvalidOnlyCRnoLF());
            fail();
        } catch (HttpParsingException e) {
            assertEquals(e.getErrorCode(),HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    private InputStream generateValidGETTestCase(){
        String rawData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "Accept-Encoding: gzip, deflate\r\n" +
                "Connection: keep-alive\r\n" +
                "Cookie: Idea-7d085fb0=f55a34ed-5870-4eb0-9911-47b94fd3cbaa\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }

    private InputStream generateBadTestCaseMethodName1(){
        String rawData = "GeT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "Accept-Encoding: gzip, deflate\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }

    private InputStream generateBadTestCaseMethodName2(){
        String rawData = "GETTTTTTTTTTTTTTTTTT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "Accept-Encoding: gzip, deflate\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }

    private InputStream generateBadTestCaseRequestLineInvalidNumberItem1(){
        String rawData = "GET / AAAAAAA HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "Accept-Encoding: gzip, deflate\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }

    private InputStream generateBadTestCaseEmptyRequestLine(){
        String rawData = "\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "Accept-Encoding: gzip, deflate\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }

    private InputStream generateBadTestCaseRequestLineInvalidOnlyCRnoLF(){
        String rawData = "GET / HTTP/1.1\r" + //<------------- no LF
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "Accept-Encoding: gzip, deflate\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "\r\n";
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(StandardCharsets.US_ASCII)
        );
        return inputStream;
    }
}
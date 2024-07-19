import Factory.ResponseFactory;
import Factory.getFactory;
import HTTPRequest.HttpRequest;
import HTTPRequest.ResponseCodes;
import ResponseProducts.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.net.http.HttpClient;

public class MessageHandler implements Runnable {

    PrintWriter HttpClientOutput;
    BufferedReader HttpClientInput;
    Socket HttpClient;
    public MessageHandler(Socket connection) throws IOException {
       this.HttpClient = connection;
       this.HttpClientInput = new BufferedReader(new InputStreamReader(connection.getInputStream(), "ISO_8859_1"));
       //Autoflush enabled
       this.HttpClientOutput = new PrintWriter(connection.getOutputStream(),true);
    }

    @Override
    public void run() {
        try {
            //Tokenize Request within the stream into {requestline, header, entity body}
            int bufferlength = HttpClient.getInputStream().available();
            byte[] requestbyte = new byte[bufferlength];
            BufferedInputStream test = new BufferedInputStream(HttpClient.getInputStream());
            test.read(requestbyte,0,bufferlength);
            String request = new String(requestbyte);
            int indexRequestLine = request.indexOf("\r\n");
            int indexEntityBody = request.indexOf("\r\n\r\n");
            String requestLine = new String(requestbyte,0,indexRequestLine);
            String requestHeaders = new String(requestbyte,indexRequestLine,indexEntityBody-1);
            int lengthEntityBody=bufferlength-(indexEntityBody+4);
            byte[] requestEntityBody = new byte[lengthEntityBody];
            for(int i = 0; i<lengthEntityBody; i++){
                requestEntityBody[i] = requestbyte[indexEntityBody+4+i]; }
            //Incorporate error handling here. If any of these indexOf returns -1, there is a bad request format error

            //Encapsulating client's request attributes/functionality in object HTTPRequest.HttpRequest
            //Add constructor for byte
            //Using inner class to validate enclosing http request class attribuets
            HttpRequest client = new HttpRequest(requestLine,requestHeaders,requestEntityBody);
            HttpRequest.HttpRequestValidator validate = client.new HttpRequestValidator();
            if(validate.validateThenSetRequestToken() && validate.validateThenSetHeaderToken()) {
                //Add error handling mechanism, in case request contains an invalid token
                ResponseFactory httpresponsefactory = null;
                switch (client.getRequestMethod()) {
                    case "GET":
                        httpresponsefactory = new getFactory();
                        break;
                    default:
                        ResponseFactory.SendHttpResponse(HttpClientOutput, ResponseCodes.R501);
                        HttpClient.close();
                }
                if (!(client.getRequestURI().exists() && client.getRequestURI().isFile())) {
                    ResponseFactory.SendHttpResponse(HttpClientOutput, ResponseCodes.R404);
                } else {
                    assert (httpresponsefactory != null);
                    //Telling the webserver to respond, which will respond based on the client's http request
                    ResponseFactory.SendHttpResponse(HttpClientOutput, httpresponsefactory.processRequest(client));
                }
            }
            else {
                //Sending 400 bad request format
                ResponseFactory.SendHttpResponse(HttpClientOutput, ResponseCodes.R400);
            }
            HttpClient.close();
        } catch(IOException e){
            System.out.println("error");
        } catch (AssertionError e){

        }
    }
}

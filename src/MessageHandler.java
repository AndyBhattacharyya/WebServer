import Factory.ResponseFactory;
import Factory.getFactory;
import HTTPRequest.HttpRequest;
import HTTPRequest.ResponseCodes;
import ResponseProducts.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
            //Transforming stream of bytes into stream of characters with ASCII decoding
            char[] tmp = new char[HttpClient.getInputStream().available()];
            HttpClientInput.read(tmp);
            //Creating String to represent entire request
            String req = new String(tmp);
            //Validate Syntax/Structure of request
            if(ResponseFactory.validRequest(req)){
                //Encapsulating client's request attributes/functionality in object HTTPRequest.HttpRequest
                HttpRequest client = new HttpRequest(req);
                //Determine which factory to use
                ResponseFactory httpresponsefactory=null;
                switch(client.getHttp_method()){
                    case "GET":
                        httpresponsefactory = new getFactory();
                        break;
                    default:
                        ResponseFactory.SendHttpResponse(HttpClientOutput, ResponseCodes.R501);
                        HttpClient.close();
                }
                if(!(client.getHttp_requesturi().exists()&&client.getHttp_requesturi().isFile())){
                    ResponseFactory.SendHttpResponse(HttpClientOutput, ResponseCodes.R404);
                }else {
                    assert (httpresponsefactory != null);
                    //Telling the webserver to respond, which will respond based on the client's http request
                    ResponseFactory.SendHttpResponse(HttpClientOutput, httpresponsefactory.processRequest(client));
                }
            }
            else{
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

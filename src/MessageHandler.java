import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageHandler implements Runnable {

    PrintWriter HttpClientOutput;
    BufferedReader HttpClientInput;
    Socket HttpClient;
    public MessageHandler(Socket connection) throws IOException {
       this.HttpClient = connection;
       this.HttpClientInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
            if(HttpRequest.validRequest(req)){
                //Encapsulating client's request attributes/functionality in object HttpRequest
                HttpRequest client = new HttpRequest(req);
                //Telling the webserver to respond, which will respond based on the client's http request
                HttpResponse.SendHttpResponse(HttpClientOutput,new HttpResponse(client));
            }
            else{
               //Sending 400 bad request format
                HttpResponse.SendHttpResponse(HttpClientOutput,ResponseCodes.R400);
            }
            HttpClient.close();
        } catch(IOException e){System.out.println("error");}
    }
}

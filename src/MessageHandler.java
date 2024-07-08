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

    private boolean validRequest(String req){
        /**
         * Request-Line regex checklist: 3 letter capital word =^GET , space=\s, /, space=\s, HTTP/any digit,.,any digit:
         * Request-Line + Headers Checklist:
         *
         */
        //Valid if it is a valid GET req + only mentions resource: Server ROOT
        String regex_requestline ="^(GET|POST)\\s/.+\\sHTTP/(1.0|1.1|2|3)\\s\\s";
        //Requires to match CRLF null line between Headers and Body of HTTP request
        String regex_body="(.+:\\s.+(\\s\\s|\\s))+\\s\\s";
        String regex_entitybody=".*";
        return Pattern.matches(regex_requestline+regex_body+regex_entitybody,req);
    }

   private void webserverResponse(String req, PrintWriter httpClientOutput){
        //Generating Status-Line

        //Check for validity of response request

   }





    @Override
    public void run() {
        //Handling HTTP GET Method: (1) Validate REQ Line, (2) Validate Headers till CRLF
        //Attempt to get all bytes within the stream, and turn into 1 entire string for parsing
        try {
            char[] tmp = new char[HttpClient.getInputStream().available()];
            HttpClientInput.read(tmp);
            //Obtained our string to apply regex filters string is multilined denoted by ASCII control characters 13,10
            String req = new String(tmp);
            if(validRequest(req)){
                HttpRequest client = new HttpRequest(req);
                System.out.println(client.getHttp_requestLine());
            }
            //Requires to match CRLF null line between Headers and Body of HTTP request
            /*
            String regex_requestline ="^POST\\s/.+\\sHTTP/(1.0|1.1|2|3)(\\s|\\s\\s)";
            String regex_headerbody="(.+:\\s.+(\\s\\s|\\s))+\\s\\s";
            //Require lookbehind regex so we can identify the EntityBody only if the null precedes it
            String regex_entitybody = "(?<=^\\s\\s).+";
            Matcher requestline = Pattern.compile(regex_requestline).matcher(req);
            for(int i : req.getBytes()){
                System.out.print(i+",");
            }
             */
        } catch(IOException e){System.out.println("error");}
    }
}

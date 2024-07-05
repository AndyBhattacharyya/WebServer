import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
        String regex_requestline ="^GET\\s/\\sHTTP/(1.0|1.1|2|3)(\\s|\\s\\s)";
        String regex_body="(.+:\\s.+(\\s\\s|\\s))+\\s\\s";
        return Pattern.matches(regex_requestline+regex_body,req);
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
            System.out.println(req);
            System.out.println(validRequest(req));
            /*
            for(int i : req.getBytes()){
                System.out.print(i+",");
            }
            */
        } catch(IOException e){}
    }
}

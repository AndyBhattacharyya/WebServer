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
       this.HttpClientInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       //Autoflush enabled
       this.HttpClientOutput = new PrintWriter(connection.getOutputStream(),true);
    }

    @Override
    public void run() {
        //Handling HTTP GET Method: (1) Validate REQ Line, (2) Validate Headers till CRLF
        //Attempt to get all bytes within the stream, and turn into 1 entire string for parsing
        try {
            char[] tmp = new char[HttpClient.getInputStream().available()];
            HttpClientInput.read(tmp);
            //Obtained our string to apply regex filters
            String req = new String(tmp);
            System.out.println(req);
        } catch(IOException e){}
    }
}

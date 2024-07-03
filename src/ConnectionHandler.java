import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {

    public static void main(String[] args) throws IOException {
        final ServerSocket http_server = new ServerSocket(25565);
        String message = "";
        Socket http_client = http_server.accept();
        BufferedReader http_input = new BufferedReader(new InputStreamReader(http_client.getInputStream()));
        do{
            try {
                message = http_input.readLine();
                System.out.println(message);
                //http_client.close();
            }catch(IOException e){
                http_client.close();
                System.out.println(http_client.isClosed());
            }
        }while(!http_client.isClosed());

    }
}
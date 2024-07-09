import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionHandler {

    public static void main(String[] args) throws IOException {
        //Make this loop until interrupt
        final ServerSocket http_server = new ServerSocket(25565);
        ExecutorService request_handler = Executors.newFixedThreadPool(3);
        while(true) {
            request_handler.submit(new MessageHandler(http_server.accept()));
        }

    }
}
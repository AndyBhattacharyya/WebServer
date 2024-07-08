import java.io.PrintWriter;

public class HttpResponse extends HttpMessage{

    public static void SendHttpResponse(HttpRequest tmp, PrintWriter client_output){
        //Hardcoded HTTP version
        String http_version = "HTTP/1.0";
        String Status_Code = tmp.getClientResponsecode().getResponseCode();
        String Status_Phrase = tmp.getClientResponsecode().getResponsePhrase();
        String Status_Line = http_version+" "+Status_Code+" "+Status_Phrase+"\r\n";
        client_output.println(Status_Line);
    }
}

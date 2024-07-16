package ResponseProducts;

import java.io.*;
import java.util.regex.Pattern;
import HTTPRequest.*;

public class HttpResponseGet extends HttpResponse {

    public HttpResponseGet(HttpRequest client_request){
        //sets up status line
        super(client_request);
    }

    private String getFileContent(File URI){
        try {
            FileInputStream read = new FileInputStream(URI);
            //Same strategy above to serving HTML files
            BufferedReader readchar = new BufferedReader(new InputStreamReader(read));
            char[] tmp = new char[read.available()];
            readchar.read(tmp);
            return new String(tmp);
        } catch (IOException e) {
            System.out.println("Error");
        }
        return "";
    }

   public void processRequest(){
        File URI = client_request.getHttp_requesturi();
       //Setting Content-Length Header
       long bytesize = URI.length();
       //Setting Content-Type Header based on extension
       if(Pattern.matches("(.+\\.html)",URI.getName())) {
           addHeaders("Content-Type: text/html\r\n");
           addHeaders("Content-Length: " + Long.toString(bytesize)+"\r\n");
           EntityBody =  getFileContent(URI);
       }
       else if(Pattern.matches("(.+\\.js)",URI.getName())) {
           addHeaders("Content-Type: text/javascript\r\n");
           addHeaders("Content-Length: " + Long.toString(bytesize)+"\r\n");
           EntityBody =  getFileContent(URI);
       }
       httpResponse = buildHttpResponse(Status_Line,responseHeaders,EntityBody);
   }
}

package ResponseProducts;

import java.io.*;
import java.util.regex.Pattern;
import HTTPRequest.*;
import HeaderCreation.ContentLengthHeader;
import HeaderCreation.ContentTypeHeader;
import HeaderCreation.Header;
import HeaderStrategies.Context;

public class HttpResponseGet extends HttpResponse {


    private Header ContentLength;
    private Header ContentType;
    public HttpResponseGet(HttpRequest client_request){
        //sets up status line and response headers to be extended
        super(client_request);
        this.ContentLength = new ContentLengthHeader(client_request.getRequestURI());
        this.ContentType = new ContentTypeHeader(client_request.getRequestURI());
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
        File URI = client_request.getRequestURI();
        //building headers
       ContentLength.createHeader(this);
       ContentType.createHeader(this);
        //building body
       EntityBody =  getFileContent(URI);
       httpResponse = buildHttpResponse(Status_Line,responseHeaders,EntityBody);
   }
   public void processHeaders(){
        Context executor = new Context();
        //Make this extensible, however this is where I will choose which headers to recognize

   }
}

package ResponseProducts;

import java.io.*;
import java.util.regex.Pattern;
import HTTPRequest.*;
import HeaderCreation.ContentLengthHeader;
import HeaderCreation.ContentTypeHeader;
import HeaderCreation.Header;
import HeaderStrategies.Context;
import HeaderStrategies.IfModifiedSinceProcess;
import HeaderStrategies.Strategy;

public class HttpResponseGet extends HttpResponse {


    private Header ContentLength;
    private Header ContentType;
    private Context headerprocessor;
    public HttpResponseGet(HttpRequest client_request){
        //sets up status line and response headers to be extended
        super(client_request);
        this.ContentLength = new ContentLengthHeader(client_request.getRequestURI());
        this.ContentType = new ContentTypeHeader(client_request.getRequestURI());
        this.headerprocessor = new Context();
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
        //Checking headers with request
       Context headerServicer = new Context();
       File URI = client_request.getRequestURI();
       String clientHeader;
       if(!(clientHeader=client_request.getHeaderTokens("If-Modified-Since")).equals("")){
           headerServicer.setStrategy(new IfModifiedSinceProcess(URI));
           headerServicer.executeStrategy(this);
       }
       if(this.getResponseStatusCode().equals(ResponseCodes.R200)) {
           //building headers
           ContentLength.createHeader(this.responseHeaders);
           ContentType.createHeader(this.responseHeaders);
           //building body
           buildEntityBody(getFileContent(URI));
           buildStatusLine(ResponseCodes.R200);
           buildResponse();
       }
   }
}

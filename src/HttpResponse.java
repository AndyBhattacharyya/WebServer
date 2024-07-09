import java.io.*;
import java.util.regex.Pattern;

public class HttpResponse extends HttpMessage{

    private final String http_version = "HTTP/1.0";
    private HttpRequest client_request;
    private String Status_Code;
    private String Status_Phrase;
    private String Status_Line;
    private String responseHeaders;
    private String EntityBody;
    private String httpResponse;

    public HttpResponse(HttpRequest clientrequest){
        this.client_request=clientrequest;
        ResponseCodes tmp = clientrequest.getClientResponsecode();
        //Handling Bad Request and Method not Supported Errors
        if(tmp.equals(ResponseCodes.R400)||tmp.equals(ResponseCodes.R501)){
            Status_Code=clientrequest.getClientResponsecode().getResponseCode();
            Status_Phrase = clientrequest.getClientResponsecode().getResponsePhrase();
            Status_Line=http_version+" "+Status_Code+" "+Status_Phrase+"\r\n";
        }
        //Can change the Status Code to 404 if resource DNE
        else if(client_request.getHttp_method().equals("GET")){
           EntityBody=this.processGetRequest(client_request.getHttp_requesturi());
        }
        Status_Code=clientrequest.getClientResponsecode().getResponseCode();
        Status_Phrase = clientrequest.getClientResponsecode().getResponsePhrase();
        Status_Line=http_version+" "+Status_Code+" "+Status_Phrase+"\r\n";
        httpResponse = Status_Line+responseHeaders+EntityBody;
    }

    public void SendHttpResponse(PrintWriter tmp){
        tmp.println(httpResponse);
    }
    private void addHeaders(String header){
        //Add a header from the base of a nullline
        responseHeaders = header+responseHeaders;
    }
    private String processGetRequest(String requestURI){
        //File Code begins
        File URI = new File(requestURI);
        if(client_request.getClientResponsecode().equals(ResponseCodes.R200)&&URI.exists() && URI.isFile()){
            //Setting Content-Length Header
            long bytesize = URI.length();
            //Setting Content-Type Header based on extension
            if(Pattern.matches("(.+\\.html)",URI.getName())) {
                addHeaders("Content-Type: text/html\r\n");
                addHeaders("Content-Length: " + Long.toString(bytesize)+"\r\n");

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
            }
            if(Pattern.matches("(.+\\.js)",URI.getName())) {
                addHeaders("Content-Type: text/javascript\r\n");
                addHeaders("Content-Length: " + Long.toString(bytesize)+"\r\n");

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
            }

        }
        client_request.setResponsecode(ResponseCodes.R404);
        return "";
    }




}

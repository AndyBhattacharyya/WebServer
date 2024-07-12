import java.io.*;
import java.util.regex.Pattern;

public class HttpResponse {

    private final static String http_version = "HTTP/1.0";
    private HttpRequest client_request;
    private String Status_Line;
    private String responseHeaders="";
    private String EntityBody="";
    private String httpResponse;
    private ResponseCodes httpResponseCode;


    public static void SendHttpResponse(PrintWriter tmp, HttpResponse httpResponse){
        tmp.println(httpResponse.getHttpResponse());
    }
    public static void SendHttpResponse(PrintWriter tmp, ResponseCodes httpStatus){

        String statusline="";
        switch(httpStatus){
            case R400:
                statusline = http_version+" "+httpStatus.getResponseCode()+" "+httpStatus.getResponsePhrase()+"\r\n";
        }
        tmp.println(statusline);
    }
    private String buildHttpResponse(String statusline, String headers, String entitybody){
        return statusline+headers+entitybody;
    }

    public String getHttpResponse() {
        return httpResponse;
    }

    public HttpResponse(HttpRequest client_request){
        //Validating request token
        this.client_request=client_request;
        //validate URI
        if(!(client_request.getHttp_requesturi().exists()&&client_request.getHttp_requesturi().isFile())){
            this.httpResponseCode=ResponseCodes.R404;
            this.Status_Line= http_version+" "+httpResponseCode.getResponseCode()+" "+httpResponseCode.getResponsePhrase()+"\r\n";
            httpResponse = buildHttpResponse(Status_Line,responseHeaders,EntityBody);
        }
        //Handle supported method validation
        else{
            if (!(client_request.getHttp_method().equals("GET")||client_request.getHttp_method().equals("POST"))){
                this.httpResponseCode=ResponseCodes.R501;
                this.Status_Line= http_version+" "+httpResponseCode.getResponseCode()+" "+httpResponseCode.getResponsePhrase()+"\r\n";
                httpResponse = buildHttpResponse(Status_Line,responseHeaders,EntityBody);
            }
            else if(client_request.getHttp_method().equals("GET")){
                //URI is valid and method supported, then we good
                this.httpResponseCode=ResponseCodes.R200;
                this.Status_Line= http_version+" "+httpResponseCode.getResponseCode()+" "+httpResponseCode.getResponsePhrase()+"\r\n";
                //Since request valid, setting reponse headers to be easily extensible by embedding null line
                responseHeaders = "\r\n";
                //Processing requests
                this.httpResponse=buildHttpResponse(Status_Line,responseHeaders,processGetRequest(client_request.getHttp_requesturi()));
            }
            //POST method not supported
        }

    }

    private void addHeaders(String header){
        //Add a header from the base of a nullline. Headers must end in \r\n ~ CRLF
        responseHeaders = header+responseHeaders;
    }
    private String processGetRequest(File URI){
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
        return "";
    }
}

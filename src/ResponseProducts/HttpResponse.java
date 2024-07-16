package ResponseProducts;

import java.io.*;
import HTTPRequest.*;
public class HttpResponse {

    public final static String http_version = "HTTP/1.0";
    HttpRequest client_request;
    String Status_Line;
    String responseHeaders="";
    String EntityBody="";
    String httpResponse;
    ResponseCodes httpResponseCode;

    String buildHttpResponse(String statusline, String headers, String entitybody){
        return statusline+headers+entitybody;
    }

    public String getHttpResponse() {
        return httpResponse;
    }

    public HttpResponse(HttpRequest client_request){
        this.client_request=client_request;
        httpResponseCode= ResponseCodes.R200;
        this.Status_Line= http_version+" "+httpResponseCode.getResponseCode()+" "+httpResponseCode.getResponsePhrase()+"\r\n";
        //Since request valid, setting reponse headers to be easily extensible by embedding null line
        responseHeaders = "\r\n";
    }

    void addHeaders(String header){
        //Add a header from the base of a nullline. Headers must end in \r\n ~ CRLF
        responseHeaders = header+responseHeaders;
    }

    public void processRequest(){
        httpResponse = "";
    }
}

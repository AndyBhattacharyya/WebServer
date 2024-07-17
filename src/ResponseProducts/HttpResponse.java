package ResponseProducts;

import java.io.*;
import HTTPRequest.*;
import HeaderCreation.DefaultDateHeader;
import HeaderCreation.DefaultExpiresHeader;
import HeaderCreation.DefaultServerHeader;
import HeaderCreation.Header;

public class HttpResponse {

    public final static String http_version = "HTTP/1.0";
    //Create Static Data Structure that holds all default headers initialized when class is loaded
    private static Header[] defaultHeaders = {
        new DefaultDateHeader(),
        new DefaultServerHeader(),
        new DefaultExpiresHeader()};

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
        //Initializing default headers
        for(Header i:defaultHeaders){
            i.createHeader(this);
        }

    }

    public void addHeaders(String header){
        //Add a header from the base of a nullline. Headers must end in \r\n ~ CRLF
        responseHeaders = header+responseHeaders;
    }

    public void processRequest(){
        httpResponse = "";
    }
    public void processHeaders(){

    }
}

package ResponseProducts;

import java.io.*;
import java.util.HashMap;

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
    private String responseStatusLine="";
    private String responseTotalHeaders="";
    private String responseEntityBody="";
    private String response="";
    private ResponseCodes responseStatusCode;
    HashMap<String,String> responseHeaders;

    void buildHeaders(){

    }
    void setResponseStatusCode(ResponseCodes responseStatusCode){
       this.responseStatusCode=responseStatusCode;
    }
    void buildEntityBody(String responseEntityBody){
        this.responseEntityBody=responseEntityBody;
    }
    void buildStatusLine(ResponseCodes responseStatusCode){
        this.responseStatusLine = http_version +" "+ responseStatusCode.getResponseCode()+" "+responseStatusCode.getResponsePhrase()+"\r\n";
    }
    void buildResponse(){
        response = this.responseStatusLine+this.responseHeaders+this.responseEntityBody;
    }

    public String getResponse() {
        return response;
    }

    public HttpResponse(HttpRequest client_request){
        this.client_request=client_request;
        //put an anonymous class here quickly overriding the hashmap toString()
        this.responseHeaders = client_request.getRequestHeaderValues();
        responseTotalHeaders = "\r\n";
        for(Header i:defaultHeaders){
            i.createHeader(this.responseHeaders);
        }
        responseStatusCode= ResponseCodes.R200;
    }

    public void processRequest(){
    }
}

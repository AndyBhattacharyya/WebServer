package Factory;

import HTTPRequest.*;
import ResponseProducts.*;

import java.io.PrintWriter;
import java.util.regex.Pattern;

import static ResponseProducts.HttpResponse.http_version;

public abstract class ResponseFactory {

    public static void SendHttpResponse(PrintWriter tmp, ResponseCodes httpStatus){
        String statusline="";
        switch(httpStatus){
            case R400:
                statusline = http_version+" "+httpStatus.getResponseCode()+" "+httpStatus.getResponsePhrase()+"\r\n";
            case R404:
                statusline = http_version+" "+httpStatus.getResponseCode()+" "+httpStatus.getResponsePhrase()+"\r\n";
            case R501:
                statusline = http_version+" "+httpStatus.getResponseCode()+" "+httpStatus.getResponsePhrase()+"\r\n";
        }
        tmp.println(statusline);
    }

    public static void SendHttpResponse(PrintWriter tmp, HttpResponse httpResponse){
        tmp.println(httpResponse.getResponse());
    }
    abstract HttpResponse createResponse(HttpRequest client);

    public ResponseFactory(){
    }

    public HttpResponse processRequest(HttpRequest client_request){
    //Caller of product functions and extensible
       HttpResponse tmp = this.createResponse(client_request);
       tmp.processRequest();
       return tmp;
    }
}

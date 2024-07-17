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
        tmp.println(httpResponse.getHttpResponse());
    }
    abstract HttpResponse createResponse(HttpRequest client);

    public ResponseFactory(){
    }

    public static boolean validRequest(String req){
        /**
         * Precondition to HTTP req constructor
         */
        //Valid if it is a valid GET req with proper URL encoding. Utilize right library
        String regex_requestline ="^[A-Z]+\\s/[A-Za-z0-9[%.]]*\\sHTTP/(1.0|1.1|2|3)\\s\\s";
        //Requires to match CRLF null line between Headers and Body of HTTP request
        String regex_body="(.+:\\s.+(\\s\\s|\\s))+\\s\\s";
        String regex_entitybody=".*";
        return Pattern.matches(regex_requestline+regex_body+regex_entitybody,req);
    }

    public HttpResponse processRequest(HttpRequest client_request){
        //Caller of product functions and extensible
       HttpResponse tmp = this.createResponse(client_request);
       tmp.processRequest();
       tmp.processHeaders();
       return tmp;
    }
}

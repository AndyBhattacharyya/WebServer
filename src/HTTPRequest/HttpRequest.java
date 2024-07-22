package HTTPRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {
    private String requestMethod;
    private File requestURI;
    private String requestVersion;
    private String requestLine;
    private byte[] requestEntityBody;
    private String requestHeader;
    private HashMap <String,String> requestHeaderValues;

    public HttpRequest(String requestLine, String requestHeader, byte[] requestEntityBody){
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestEntityBody = requestEntityBody;
    }

    public String getHeaderTokens(String headerkey){
        String headervalue;
        headervalue = requestHeaderValues.containsKey(headerkey)?requestHeaderValues.get(headerkey):"";
        return headervalue;
    }

    public HashMap<String, String> getRequestHeaderValues() {
        return requestHeaderValues;
    }

    public void setHeaderTokens(String headers, HashMap<String,String> tokens){
        headers = headers.trim();
        String[] header_values = headers.split("\\s\\s");
        for(String i:header_values){
            String[] header_tokens = i.split(":\\s");
            tokens.put(header_tokens[0],header_tokens[1]);
        }
    }
    public void setRequestTokens(){
        Scanner requestLine = new Scanner(this.requestLine);
        requestMethod = requestLine.next().trim();
        //Setting request URI relative to BASE URI of web root specified by user.dir
        String resource = requestLine.next().trim();
        if(resource.equals("/")) {
            requestURI = new File(System.getProperty("user.dir") + "/game.html");
        }
        else {
            requestURI = new File(System.getProperty("user.dir") + resource);
        }
        requestVersion =requestLine.next().trim();
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public File getRequestURI() {
        return requestURI;
    }

    public String getRequestVersion() {
        return requestVersion;
    }

    public byte[] getRequestEntityBody() {
        return requestEntityBody;
    }

    public String getRequestLine() {
        return requestLine;
    }

    public class HttpRequestValidator implements ValidHttpRequest  {

        @Override
        public boolean validateThenSetRequestToken() {
            //Tokenizing Request to validate syntax
            String regex_requestline ="^[A-Z]*\\s/.*\\sHTTP/(1\\.0|1\\.1|2|3)";
            boolean tmp =Pattern.matches(regex_requestline,requestLine);
            if (tmp) {
                setRequestTokens();
            }
            return tmp;
        }

        @Override
        public boolean validateThenSetHeaderToken() {
            //Tokenize header body as well as null line
            //remove this since there is only a request line and a regex_headerbody
            String regex_headerbody="(.+:\\s.+(\\s\\s)?)+";
            boolean tmp =Pattern.matches(regex_headerbody,requestHeader);
            if (tmp) {
                requestHeaderValues = new HashMap<String,String>();
                setHeaderTokens(requestHeader,requestHeaderValues);
            }
            return tmp;
        }
    }
}

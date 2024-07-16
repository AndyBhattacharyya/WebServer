package HTTPRequest;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {

    private String http_method;
    private File http_requesturi;
    private String http_version;
    private String http_requestLine;
    private String http_entityBody;
    private String http_headerBody;
    private String req;

    public HttpRequest(String req){
        //Not yet validated (raw) request
        this.req=req;
        //Tokenizing Request to validate semantics
        //Tokenize request line
        String regex_requestline ="^[A-Z]*\\s/.*\\sHTTP/(1\\.0|1\\.1|2|3)(\\s\\s)";
        //Tokenize header body as well as null line
        String regex_headerbody="(.+:\\s.+(\\s\\s|\\s))+\\s\\s";
        //Require lookbehind regex so we can identify the EntityBody only if the null line precedes it. Tokenize Entity Body
        String regex_entitybody = "(?<=^\\s\\s).*";
        //Obtain tokens as strings
        Matcher httpparse = Pattern.compile(regex_requestline).matcher(req);
        httpparse.find();
        http_requestLine= httpparse.group();
        httpparse = Pattern.compile(regex_headerbody).matcher(req);
        httpparse.find();
        http_headerBody= httpparse.group();
        httpparse = Pattern.compile(regex_entitybody,Pattern.MULTILINE).matcher(req);
        httpparse.find();
        http_entityBody= httpparse.group();
        //Further Tokenizing of HTTP requestline to ensure meaningful request:verb, requestURI,etc
        Scanner requestline = new Scanner(http_requestLine);
        //Obtaining tokenized HTTP requestline
        setRequestTokens(requestline);
    }

    public String getReq() {
        return req; }

    private void setRequestTokens(Scanner requestLine){
        http_method = requestLine.next().trim();
        //Setting request URI relative to BASE URI of web root specified by user.dir
        String resource = requestLine.next().trim();
        if(resource.equals("/")) {
            http_requesturi = new File(System.getProperty("user.dir") + "/game.html");
        }
        else {
            http_requesturi = new File(System.getProperty("user.dir") + resource);
        }
        http_version =requestLine.next().trim();
    }

    public String getHttp_method() {
        return http_method;
    }

    public File getHttp_requesturi() {
        return http_requesturi;
    }

    public String getHttp_version() {
        return http_version;
    }

    public String getHttp_entityBody() {
        return http_entityBody;
    }

    public String getHttp_requestLine() {
        return http_requestLine;
    }

    public String getHttp_headerBody() {
        return http_headerBody;
    }
}

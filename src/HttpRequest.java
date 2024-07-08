import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest extends HttpMessage{
    //Request Line HTTP
    private String http_requestLine;
    //Constituents
    private String method;
    private String Request_URI;
    private String http_entityBody;
    private String http_headerBody;

    public HttpRequest(String req){
        String regex_requestline ="^(POST|GET)\\s/.+\\sHTTP/(1.0|1.1|2|3)(\\s|\\s\\s)";
        String regex_headerbody="(.+:\\s.+(\\s\\s|\\s))+\\s\\s";
        //Require lookbehind regex so we can identify the EntityBody only if the null precedes it
        String regex_entitybody = "(?<=^\\s\\s).*";
        Matcher httpparse = Pattern.compile(regex_requestline).matcher(req);
        httpparse.find();
        http_requestLine= httpparse.group();
        httpparse = Pattern.compile(regex_headerbody).matcher(req);
        httpparse.find();
        http_headerBody= httpparse.group();
        httpparse = Pattern.compile(regex_entitybody,Pattern.MULTILINE).matcher(req);
        httpparse.find();
        http_entityBody= httpparse.group();
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

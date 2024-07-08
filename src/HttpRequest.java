import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest extends HttpMessage{

    private String http_method;
    private String http_requesturi;
    private String http_version;
    private String http_requestLine;
    private String http_entityBody;
    private String http_headerBody;
    private String req;
    private ResponseCodes responsecode;

    public HttpRequest(String req){
        //Not yet validated (raw) request
        this.req=req;
        //Checks if the syntax of the request follows RFC HTTP 1.0
        if(!validRequest(req)){
            this.responsecode=ResponseCodes.R400;
        }
        else{
            //Tokenizing Request to validate semantics
            //Tokenize request line
            String regex_requestline ="^[A-Z]*\\s/.*\\sHTTP/(1.0|1.1|2|3)(\\s\\s)";
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
            //Validating the tokenized HTTP requestline
            validateRequestTokens();
        }
    }

    public ResponseCodes getClientResponsecode() {
        return responsecode;
    }

    public String getReq() {
        return req;
    }

    public void validateRequestTokens(){
        //Only supporting GET and POST methods for now
        if(!http_method.equals("GET")&&!http_method.equals("POST")){
            this.responsecode =ResponseCodes.R501;
            return;
        }
        //Only support ServerRoot Resource
        if(!http_requesturi.equals("/")){
            this.responsecode =ResponseCodes.R404;
            return;
        }
        this.responsecode = ResponseCodes.R200;
    }

    private boolean validRequest(String req){
        /**
         * Request-Line regex checklist: 3 letter capital word =^GET , space=\s, /, space=\s, HTTP/any digit,.,any digit:
         * Request-Line + Headers Checklist:
         *
         */
        //Valid if it is a valid GET req + only mentions resource: Server ROOT
        String regex_requestline ="^[A-Z]+\\s/.*\\sHTTP/(1.0|1.1|2|3)\\s\\s";
        //Requires to match CRLF null line between Headers and Body of HTTP request
        String regex_body="(.+:\\s.+(\\s\\s|\\s))+\\s\\s";
        String regex_entitybody=".*";
        return Pattern.matches(regex_requestline+regex_body+regex_entitybody,req);
    }

    private void setRequestTokens(Scanner requestLine){
        http_method = requestLine.next().trim();
        http_requesturi = requestLine.next().trim();
        http_version =requestLine.next().trim();
    }

    public String getHttp_method() {
        return http_method;
    }

    public String getHttp_requesturi() {
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

package HeaderCreation;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ContentTypeHeader implements Header{

    private File URI;
    private String headerField;
    private String headerValue;
    public ContentTypeHeader(File URI){
        this.headerField = "Content-Type";
        this.URI = URI;
        //List of supported types need to be extended, for now js,html
    }
    @Override
    public void createHeader(HashMap<String, String> tmp) {
        headerValue="";
        if(Pattern.matches("(.+\\.html)",URI.getName())) {
            headerValue = "Content-Type: text/html\r\n";
        }
        else if(Pattern.matches("(.+\\.js)",URI.getName())) {
            headerValue = "Content-Type: text/javascript\r\n";
        }
        tmp.put(headerField,headerValue);
    }
}

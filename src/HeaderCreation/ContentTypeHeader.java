package HeaderCreation;

import ResponseProducts.HttpResponse;

import java.io.File;
import java.util.regex.Pattern;

public class ContentTypeHeader implements Header{

    private File URI;
    public ContentTypeHeader(File URI){
        this.URI = URI;
        //List of supported types need to be extended, for now js,html
    }
    @Override
    public void createHeader(HttpResponse tmp) {
        String header="";
        if(Pattern.matches("(.+\\.html)",URI.getName())) {
            header = "Content-Type: text/html\r\n";
        }
        else if(Pattern.matches("(.+\\.js)",URI.getName())) {
            header = "Content-Type: text/javascript\r\n";
        }
        tmp.addHeaders(header);
    }
}

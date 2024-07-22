package HeaderCreation;

import java.io.File;
import java.util.HashMap;

public class ContentLengthHeader implements Header{

    private File URI;
    private String headerField;
    private String headerValue;
    public ContentLengthHeader(File URI){
        headerField = "Content-Length";
        this.URI = URI;
    }
    @Override
    public void createHeader(HashMap<String, String> tmp) {
        headerValue = Long.toString(URI.length());
        tmp.put(headerField,headerValue);
    }
}

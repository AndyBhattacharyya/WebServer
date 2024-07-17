package HeaderCreation;

import ResponseProducts.HttpResponse;

import java.io.File;

public class ContentLengthHeader implements Header{

    private File URI;
    public ContentLengthHeader(File URI){
        this.URI = URI;
    }
    @Override
    public void createHeader(HttpResponse tmp) {
        String bytesize = Long.toString(URI.length());
        tmp.addHeaders("Content-Length: "+bytesize+"\r\n");
    }
}

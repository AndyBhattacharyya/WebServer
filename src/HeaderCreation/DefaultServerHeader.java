package HeaderCreation;

import java.util.HashMap;

public class DefaultServerHeader implements Header{
    private String headerField;
    private String headerValue;
    public DefaultServerHeader(String servertoken) {
        this();
        this.headerValue = servertoken;
    }

    public DefaultServerHeader(){
        this.headerField = "Server";
        this.headerValue = "Andy Games";
    }
    @Override
    public void createHeader(HashMap<String, String> tmp) {
        tmp.put(headerField,headerValue);
    }
}

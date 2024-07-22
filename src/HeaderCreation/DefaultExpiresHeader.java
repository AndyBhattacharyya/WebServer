package HeaderCreation;

import java.util.HashMap;

public class DefaultExpiresHeader extends DefaultDateHeader{

    public DefaultExpiresHeader(){
        super();
    }
    @Override
    public void createHeader(HashMap<String, String> tmp) {
        super.initDate();
        tmp.put(headerField,headerValue);
    }
}

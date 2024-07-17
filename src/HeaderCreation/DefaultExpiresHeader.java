package HeaderCreation;

import ResponseProducts.HttpResponse;

public class DefaultExpiresHeader extends DefaultDateHeader{

    public DefaultExpiresHeader(){
        super();
    }
    @Override
    public void createHeader(HttpResponse tmp) {
        super.initDate();
        tmp.addHeaders("Expires: "+date+"\r\n");
    }
}

package HeaderCreation;

import ResponseProducts.HttpResponse;

public class DefaultServerHeader implements Header{
    private String servertoken;
    public DefaultServerHeader(String servertoken){
        this.servertoken = servertoken;
    }
    public DefaultServerHeader(){
        this.servertoken = "AndyTextGames";
    }
    @Override
    public void createHeader(HttpResponse tmp) {
        tmp.addHeaders("Server: "+servertoken+"\r\n");
    }
}

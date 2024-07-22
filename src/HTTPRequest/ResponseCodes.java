package HTTPRequest;

public enum ResponseCodes {
    R200("OK"),R404("Not Found"),R501("Method Not Supported"),R400("Bad Request"),R304("Not Modified");

    private String ResponsePhrase;
    ResponseCodes(String phrase){
        ResponsePhrase = phrase;
    }

    public String getResponsePhrase() {
        return ResponsePhrase;
    }
    public String getResponseCode(){
        switch(this){
            case R200:
                return "200";
            case R404:
                return "404";
            case R501:
                return "501";
            case R400:
                return "400";
            case R304:
                return "304";}
        return "";
    }
}

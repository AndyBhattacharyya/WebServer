public enum ResponseCodes {
    R200("OK"),R404("Not Found"),R501("Method Not Supported");

    private String ResponsePhrase;
    ResponseCodes(String phrase){
        ResponsePhrase = phrase;
    }

    public String getResponsePhrase() {
        return ResponsePhrase;
    }
    public String getResponseCode(ResponseCodes rcode){
        switch(rcode){
            case R200:
                return "200";
            case R404:
                return "404";
            case R501:
                return "501";
        }
        return "";

    }
}

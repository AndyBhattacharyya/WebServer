package Factory;

import HTTPRequest.HttpRequest;
import ResponseProducts.HttpResponse;
import ResponseProducts.HttpResponseGet;

import java.io.PrintWriter;

public class getFactory extends ResponseFactory {

    public getFactory(){
        super();
    }

    public HttpResponse createResponse(HttpRequest client_request){
        return new HttpResponseGet(client_request);
    }
}

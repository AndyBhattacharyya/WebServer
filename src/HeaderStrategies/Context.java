package HeaderStrategies;

import ResponseProducts.HttpResponse;

public class Context {
    Strategy headerprocessor;

    public void setStrategy(Strategy tmp){
        headerprocessor = tmp;
    }
    public void executeStrategy(HttpResponse tmp){
        headerprocessor.processRequestHeaders(tmp);
    }
}

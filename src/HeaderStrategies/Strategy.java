package HeaderStrategies;

import ResponseProducts.HttpResponse;

public interface Strategy {
    void processRequestHeaders(HttpResponse tmp);
}

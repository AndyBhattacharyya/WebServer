package HeaderStrategies;

import HTTPRequest.HttpRequest;
import HTTPRequest.ResponseCodes;
import ResponseProducts.HttpResponse;

import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class IfModifiedSinceProcess implements Strategy {

    private File URI;
    public IfModifiedSinceProcess(File URI){
        this.URI = URI;
    }
    @Override
    public void processRequestHeaders(HttpResponse tmp) {
        HttpRequest client = tmp.getClient_request();
        Date date = new Date(URI.lastModified());
        ZonedDateTime fileModifiedDate = date.toInstant().atZone(ZoneId.of("GMT"));
        DateTimeFormatter requestDateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z");
        ZonedDateTime requestDate = ZonedDateTime.parse(client.getHeaderTokens("Date"),requestDateFormat);
        //file modified > client time
        if(fileModifiedDate.compareTo(requestDate)<0 ){
            tmp.setResponseStatusCode(ResponseCodes.R304);
        }

    }
}

package HeaderCreation;

import ResponseProducts.HttpResponse;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DefaultDateHeader implements Header{
    String date;
    public DefaultDateHeader(){date="";}
    public void initDate(){
        // Get the current date and time in GMT timezone
        ZonedDateTime currentDateTime = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC);
        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);
        // Format the current date and time using the formatter
        this.date = currentDateTime.format(formatter);
    }
    @Override
    public void createHeader(HttpResponse tmp) {
        initDate();
        tmp.addHeaders("Date: "+date+"\r\n");
    }
}

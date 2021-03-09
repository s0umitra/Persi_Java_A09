package libraries;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Date {

    SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");

    public String getStringFromDate(java.util.Date d) {
        return form.format(d);
    }

    public java.util.Date getDateFromString(String d) throws ParseException {
        return form.parse(d);
    }
}

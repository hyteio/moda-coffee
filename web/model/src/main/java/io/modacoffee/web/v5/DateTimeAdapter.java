package io.modacoffee.web.v5;

import java.util.Calendar;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateTimeAdapter extends XmlAdapter<String, Calendar> {

    public Calendar unmarshal(String value) {
    		return parseDateTime(value);
    }

    public String marshal(Calendar value) {
        return printDateTime(value);
    }

    public static String printDateTime(Calendar value) {
    		if (value == null) {
            return null;
        }
        return (javax.xml.bind.DatatypeConverter.printDateTime(value));
    }
    
    public static Calendar parseDateTime(String value) {
        return (javax.xml.bind.DatatypeConverter.parseDateTime(value));
    }
}

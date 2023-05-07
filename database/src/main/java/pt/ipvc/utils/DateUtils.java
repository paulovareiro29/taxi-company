package pt.ipvc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");

    public static String format(Date date) {
        return dateFormat.format(date);
    }

}

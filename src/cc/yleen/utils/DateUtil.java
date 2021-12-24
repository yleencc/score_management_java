package cc.yleen.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String DateToYYMMDD(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return "" + calendar.get(Calendar.YEAR) + (calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.DAY_OF_MONTH);
    }
}

package cc.yleen.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String DateToYYMMDD(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return "" + calendar.get(Calendar.YEAR) + (calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.DAY_OF_MONTH);
    }

    // YY-MM-DD 转化成Date类
    public static Date yyyy_MM_ddToDate(String yy_mm_dd) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// 定义一个formate
        Date date = simpleDateFormat.parse(yy_mm_dd);// 将format型转化成Date数据类型
        return date;
    }
}

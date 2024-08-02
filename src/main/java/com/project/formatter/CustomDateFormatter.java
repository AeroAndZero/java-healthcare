package com.project.formatter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CustomDateFormatter {
    public static String FULL_TIME_DATE = "h:mm a dd/LLL/yyyy";
    public static String DATE_ONLY = "dd/LLL/yyyy";
    public static String TIME_ONLY = "h:mm a";

    public static Date getDate(String date, String format){
        Date d = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            d = sdf.parse(date);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return d;
    }

    public static String getDateTimeStr(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_TIME_DATE);
        return sdf.format(d);
    }

    public static String getDateOnlyStr(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_ONLY);
        return sdf.format(d);
    }

    public static String getDateOnlyStr(LocalDate d){
        Date date = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_ONLY);
        return sdf.format(date);
    }

    public static String getTimeOnlyStr(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_ONLY);
        return sdf.format(d);
    }

}

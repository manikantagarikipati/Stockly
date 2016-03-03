package com.winneredge.stockly.wcommons.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Manikanta on 2/29/2016.
 */
public class DateUtil {

    public static String getDateInString(Date date){
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  dateFormat.format(date);
    }


    public static String getCurrentDate(){
        final Calendar cal = Calendar.getInstance();
        Date date =  cal.getTime();
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MMM-yyyy");
        return  simpleDateFormat.format(date);
    }

}

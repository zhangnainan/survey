package com.sg.survey.util;

import com.sg.survey.Validator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

    public static String format(Date date,String pattern){
        if(Validator.isEmpty(pattern)){
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(date);
    }
}

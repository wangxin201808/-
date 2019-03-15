package com.conner.shop.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

    public static String TimeStamp2Date(String timestampString, String formats) {
        if (StringUtils.isBlank(formats)){
            formats = "yyyy-MM-dd HH:mm:ss";
        }
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats).format(new Date(timestamp));
        return date;
    }
}

package com.demo.webdemo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String getCurrentDateFormate() {
        Date date = new Date(System.currentTimeMillis());
        String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return result;
    }
}

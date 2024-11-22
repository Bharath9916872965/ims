package com.vts.ims.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatConverter 
{
    
    public static String getDateTimeFormat(String value) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    	LocalDateTime dateTime = LocalDateTime.parse(value, inputFormatter);
    	return dateTime.format(dateTimeFormatter);
    }
}

package com.karros.vn.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.springframework.util.StringUtils;

public class Utils {
  public static boolean inLength(String val, int max) {
    if(StringUtils.isEmpty(val)) {
      return false;
    }
    
    return val.length() <= max;
  }
  
  public static boolean inLength(String val, int min, int max) {
    if(val == null) {
      return false;
    }
    
    return val.length() <= max && val.length() >= min;
  }
  
  public static LocalDateTime parseLocalDateTimeUtc(String date) {
    if(StringUtils.isEmpty(date)) {
      return null;
    }
    DateTimeFormatter utcFormatter =
        new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_LOCAL_DATE_TIME).appendLiteral('Z').toFormatter();
    return LocalDateTime.parse(date, utcFormatter);
  }
}

package com.karros.vn.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class I18N {
	public static MessageSource messageSource;
	
	public static String msg(String key) {
		return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
	}
	
	public static String msg(String key, String...strings) {
		return messageSource.getMessage(key, strings, LocaleContextHolder.getLocale());
	}
}

package com.djavafactory.pttech.rrm.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class BaseController {

    @Autowired
    private MessageSource messageSource;

    public String getResourceText(String text) {
        return messageSource.getMessage(text, null, LocaleContextHolder.getLocale());
    }

    public String getResourceText(String text, Object[] param) {
        return messageSource.getMessage(text, param, LocaleContextHolder.getLocale());
    }
}

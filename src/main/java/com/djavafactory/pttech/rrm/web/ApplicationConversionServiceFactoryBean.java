package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Acquirer;
import com.djavafactory.pttech.rrm.domain.TerminalType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;

import java.util.Map;

/**
 * A central place to register application Converters and Formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

    Converter<Acquirer, String> getAcquirerConverter() {
        return new Converter<Acquirer, String>() {
            public String convert(Acquirer acquirer) {
                return new StringBuilder().append(acquirer.getName()).toString();
            }
        };
    }

   org.springframework.core.convert.converter.Converter<TerminalType, String> getTerminalTypeConverter() {
        return new org.springframework.core.convert.converter.Converter<TerminalType, String>() {
            public String convert(TerminalType terminaltype) {
                return new StringBuilder().append(terminaltype.getName()).toString();
            }
        };
    }

    org.springframework.core.convert.converter.Converter<Map, String> getStatusCodeConverter() {
        return new org.springframework.core.convert.converter.Converter<Map, String>() {
            public String convert(Map map) {
                return new StringBuilder().append(map.get("value")).toString();
            }
        };
    }

    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
        getObject().addConverter(getStatusCodeConverter());
    }
	
}

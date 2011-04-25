// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.Configuration;
import com.djavafactory.pttech.rrm.domain.Firmware;
import com.djavafactory.pttech.rrm.domain.Param;
import com.djavafactory.pttech.rrm.domain.Province;
import com.djavafactory.pttech.rrm.domain.ReloadRequest;
import com.djavafactory.pttech.rrm.domain.Terminal;
import java.lang.String;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    Converter<Configuration, String> ApplicationConversionServiceFactoryBean.getConfigurationConverter() {
        return new Converter<Configuration, String>() {
            public String convert(Configuration configuration) {
                return new StringBuilder().append(configuration.getConfigKey()).append(" ").append(configuration.getConfigValue()).append(" ").append(configuration.getOrdering()).toString();
            }
        };
    }
    
    org.springframework.core.convert.converter.Converter<Firmware, String> ApplicationConversionServiceFactoryBean.getFirmwareConverter() {
        return new org.springframework.core.convert.converter.Converter<Firmware, String>() {
            public String convert(Firmware firmware) {
                return new StringBuilder().append(firmware.getName()).toString();
            }
        };
    }
    
    org.springframework.core.convert.converter.Converter<Param, String> ApplicationConversionServiceFactoryBean.getParamConverter() {
        return new org.springframework.core.convert.converter.Converter<Param, String>() {
            public String convert(Param param) {
                return new StringBuilder().append(param.getCreatedBy()).append(" ").append(param.getCreatedTime()).toString();
            }
        };
    }
    
    org.springframework.core.convert.converter.Converter<Province, String> ApplicationConversionServiceFactoryBean.getProvinceConverter() {
        return new org.springframework.core.convert.converter.Converter<Province, String>() {
            public String convert(Province province) {
                return new StringBuilder().append(province.getName()).toString();
            }
        };
    }
    
    org.springframework.core.convert.converter.Converter<ReloadRequest, String> ApplicationConversionServiceFactoryBean.getReloadRequestConverter() {
        return new org.springframework.core.convert.converter.Converter<ReloadRequest, String>() {
            public String convert(ReloadRequest reloadrequest) {
                return new StringBuilder().append(reloadrequest.getTransId()).append(" ").append(reloadrequest.getStatus()).append(" ").append(reloadrequest.getMfgNumber()).append(" ").append(reloadrequest.getReloadAmount()).toString();
            }
        };
    }
    
    org.springframework.core.convert.converter.Converter<Terminal, String> ApplicationConversionServiceFactoryBean.getTerminalConverter() {
        return new org.springframework.core.convert.converter.Converter<Terminal, String>() {
            public String convert(Terminal terminal) {
                return new StringBuilder().append(terminal.getTerminalId()).append(" ").append(terminal.getIp()).append(" ").append(terminal.getPort()).append(" ").append(terminal.getDescription()).toString();
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getAcquirerConverter());
        registry.addConverter(getConfigurationConverter());
        registry.addConverter(getFirmwareConverter());
        registry.addConverter(getParamConverter());
        registry.addConverter(getProvinceConverter());
        registry.addConverter(getReloadRequestConverter());
        registry.addConverter(getTerminalConverter());
        registry.addConverter(getTerminalTypeConverter());
    }
    
}

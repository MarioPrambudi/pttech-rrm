// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.web;

import com.djavafactory.pttech.rrm.domain.City;
import com.djavafactory.pttech.rrm.domain.ConfValidityPeriod;
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
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(new CityConverter());
        registry.addConverter(new ConfValidityPeriodConverter());
        registry.addConverter(new ConfigurationConverter());
        registry.addConverter(new FirmwareConverter());
        registry.addConverter(new ParamConverter());
        registry.addConverter(new ProvinceConverter());
        registry.addConverter(new ReloadRequestConverter());
        registry.addConverter(new TerminalConverter());
    }
    
    static class com.djavafactory.pttech.rrm.web.ApplicationConversionServiceFactoryBean.CityConverter implements Converter<City, String>  {
        public String convert(City city) {
            return new StringBuilder().append(city.getCityName()).toString();
        }
        
    }
    
    static class com.djavafactory.pttech.rrm.web.ApplicationConversionServiceFactoryBean.ConfValidityPeriodConverter implements Converter<ConfValidityPeriod, String>  {
        public String convert(ConfValidityPeriod confValidityPeriod) {
            return new StringBuilder().append(confValidityPeriod.getConfigKey()).append(" ").append(confValidityPeriod.getConfigValue()).append(" ").append(confValidityPeriod.getStartDate()).append(" ").append(confValidityPeriod.getEndDate()).toString();
        }
        
    }
    
    static class com.djavafactory.pttech.rrm.web.ApplicationConversionServiceFactoryBean.ConfigurationConverter implements Converter<Configuration, String>  {
        public String convert(Configuration configuration) {
            return new StringBuilder().append(configuration.getConfigKey()).append(" ").append(configuration.getConfigValue()).append(" ").append(configuration.getOrdering()).toString();
        }
        
    }
    
    static class com.djavafactory.pttech.rrm.web.ApplicationConversionServiceFactoryBean.FirmwareConverter implements Converter<Firmware, String>  {
        public String convert(Firmware firmware) {
            return new StringBuilder().append(firmware.getName()).append(" ").append(firmware.getCreatedBy()).append(" ").append(firmware.getCreatedTime()).append(" ").append(firmware.getVersionExt()).toString();
        }
        
    }
    
    static class com.djavafactory.pttech.rrm.web.ApplicationConversionServiceFactoryBean.ParamConverter implements Converter<Param, String>  {
        public String convert(Param param) {
            return new StringBuilder().append(param.getCreatedBy()).append(" ").append(param.getCreatedTime()).toString();
        }
        
    }
    
    static class com.djavafactory.pttech.rrm.web.ApplicationConversionServiceFactoryBean.ProvinceConverter implements Converter<Province, String>  {
        public String convert(Province province) {
            return new StringBuilder().append(province.getName()).toString();
        }
        
    }
    
    static class com.djavafactory.pttech.rrm.web.ApplicationConversionServiceFactoryBean.ReloadRequestConverter implements Converter<ReloadRequest, String>  {
        public String convert(ReloadRequest reloadRequest) {
            return new StringBuilder().append(reloadRequest.getTransId()).append(" ").append(reloadRequest.getStatus()).append(" ").append(reloadRequest.getMfgNumber()).append(" ").append(reloadRequest.getReloadAmount()).toString();
        }
        
    }
    
    static class com.djavafactory.pttech.rrm.web.ApplicationConversionServiceFactoryBean.TerminalConverter implements Converter<Terminal, String>  {
        public String convert(Terminal terminal) {
            return new StringBuilder().append(terminal.getTerminalId()).append(" ").append(terminal.getIp()).append(" ").append(terminal.getPort()).append(" ").append(terminal.getDescription()).toString();
        }
        
    }
    
}

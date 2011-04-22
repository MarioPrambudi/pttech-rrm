package com.djavafactory.pttech.rrm.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findConfigurationsByConfigKey" })
public class Configuration {

    @NotNull
    @Column(unique = true)
    private String configKey;

    private String configValue;

    @NotNull
    private Integer ordering;
}

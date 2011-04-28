package com.djavafactory.pttech.rrm.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findConfigurationsByConfigKey", "findConfigurationsByConfigKeyLike" })
public class Configuration {
    public static final String CONFIG_CELCOM_PREFIX = "my.com.celcom";
    public static final String CONFIG_TNG_PREFIX = "my.com.touchngo";
    public static final String CONFIG_RRM_PREFIX = "com.djavafactory.pttech.rrm";

    @NotNull
    @Column(unique = true)
    private String configKey;

    private String configValue;

    @NotNull
    private Integer ordering;
}

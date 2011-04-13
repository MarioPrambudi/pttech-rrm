package com.djavafactory.pttech.rrm.domain;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
public class Configuration {

    @NotNull
    @Column(unique = true)
    private String configKey;

    private String configValue;

    @NotNull
    private Integer ordering;
}

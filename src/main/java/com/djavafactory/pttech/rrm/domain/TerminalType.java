package com.djavafactory.pttech.rrm.domain;

import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.beans.factory.annotation.Value;

@RooJavaBean
@RooToString
@RooEntity
public class TerminalType {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @Value("false")
    private Boolean deleted;
}

package com.djavafactory.pttech.rrm.domain;

import javax.persistence.Basic;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
public class Firmware {

	private String name;
    @Lob
    @Basic(fetch = javax.persistence.FetchType.LAZY)
    private byte[] firmwareFile;

    private Boolean active;

    @NotNull
    @ManyToOne
    private Acquirer acquirer;
}

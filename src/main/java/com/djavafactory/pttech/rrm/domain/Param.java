package com.djavafactory.pttech.rrm.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
public class Param {

	@Lob
	@Basic(fetch=javax.persistence.FetchType.LAZY)
    private byte[] parameterFile;

	private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date createdTime;
}

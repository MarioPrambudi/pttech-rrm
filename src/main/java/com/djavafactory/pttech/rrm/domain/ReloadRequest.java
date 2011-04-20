package com.djavafactory.pttech.rrm.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findReloadRequestsByTransId" })
public class ReloadRequest {

    @NotNull
    @Column(unique = true)
    private String transId;

    private String status;

    private Long mfgNumber;

    private BigDecimal reloadAmount;

    private String serviceProviderId;

    private Integer transCode;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date requestedTime;

    private String tngKey;
}

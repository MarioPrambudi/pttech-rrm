package com.djavafactory.pttech.rrm.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@RooJavaBean
@RooToString
@RooJson
public class ReloadRequestMessage {

    private String transId;

    private String mfgNo;

    private BigDecimal amount;

    private String SPID;

    private String transCode;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date requestTime;

    private String encryptedMsg;

    private String msgType;
}

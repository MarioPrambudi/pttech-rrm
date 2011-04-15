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

    private String trans_id;

    private String mfg_no;

    private BigDecimal amount;

    private String SPID;

    private String trans_code;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date request_datetime;

    private String encrypted_msg;

    private String msg_type;
}

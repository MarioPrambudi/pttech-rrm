package com.djavafactory.pttech.rrm.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
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
@RooJson(toJsonMethod = "toJsonString", fromJsonMethod = "ReloadRequestMessage")
public class ReloadRequestMessage {

    private String transId;

    private Long mfgNo;

    private BigDecimal amount;

    private String spId;

    private Integer transCode;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date requestTime;

    private String encryptedMsg;

    private String msgType;

    public String toJsonString() {
        return new JSONSerializer().exclude("*.class").transform(new DateTransformer("ddMMyyyyHHmmss"), Date.class).serialize(this);
    }

    public ReloadRequestMessage fromJsonToMessage(String jsonString) {
        return new JSONDeserializer<ReloadRequestMessage>().use(Date.class, new DateTransformer("ddMMyyyyHHmmss")).use(null, ReloadRequestMessage.class).deserialize(jsonString);
    }
}

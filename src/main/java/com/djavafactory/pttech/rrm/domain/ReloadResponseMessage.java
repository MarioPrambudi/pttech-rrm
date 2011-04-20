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
import java.util.Date;

@RooJavaBean
@RooToString
@RooJson(toJsonMethod = "toJsonString", fromJsonMethod = "fromJsonToMessage")
public class ReloadResponseMessage {

    private String transId;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date responseTime;

    private String statusCode;

    private String statusMsg;

    public String toJsonString() {
        return new JSONSerializer().exclude("*.class").transform(new DateTransformer("ddMMyyyyHHmmss"), Date.class).serialize(this);
    }

    public ReloadResponseMessage fromJsonToMessage(String jsonString) {
        return new JSONDeserializer<ReloadResponseMessage>().use(Date.class, new DateTransformer("ddMMyyyyHHmmss")).use(null, ReloadResponseMessage.class).deserialize(jsonString);
    }
}

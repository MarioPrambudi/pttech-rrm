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
public class TerminalSyncMessage {

    private String terminalId;

    private String terminalStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date requestTime;


    public String toJsonString() {
        return new JSONSerializer().exclude("*.class").transform(new DateTransformer("ddMMyyyyHHmmss"), Date.class).serialize(this);
    }

    public TerminalSyncMessage fromJsonToMessage(String jsonString) {
        return new JSONDeserializer<TerminalSyncMessage>().use(Date.class, new DateTransformer("ddMMyyyyHHmmss")).use(null, TerminalSyncMessage.class).deserialize(jsonString);
    }
}

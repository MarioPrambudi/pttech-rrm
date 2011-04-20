package com.djavafactory.pttech.rrm.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@RooJavaBean
@RooToString
@RooJson(toJsonMethod = "toJsonString", fromJsonMethod = "fromJsonToMessage")
public class SyncTerminalMessage {

    private String acquirerName;

    private String acquirerRegistrationNo;

    private String acquirerEmail;

    private String acquirerHotline;

    private String terminalId;

    private String terminalStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date requestTime;

    @Lob
    private byte[] firmwareFile;

    @Lob
    private byte[] parameterFile;

    public String toJsonString() {
        return new JSONSerializer().exclude("*.class").transform(new DateTransformer("ddMMyyyyHHmmss"), Date.class).serialize(this);
    }

    public SyncTerminalMessage fromJsonToMessage(String jsonString) {
        return new JSONDeserializer<SyncTerminalMessage>().use(Date.class, new DateTransformer("ddMMyyyyHHmmss")).use(null, SyncTerminalMessage.class).deserialize(jsonString);
    }
}

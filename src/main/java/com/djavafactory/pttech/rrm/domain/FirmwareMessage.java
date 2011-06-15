package com.djavafactory.pttech.rrm.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import java.util.Date;

@RooJavaBean
@RooToString
@RooJson(toJsonMethod = "toJsonString", fromJsonMethod = "fromJsonToMessage")
public class FirmwareMessage {

	private String filename;

    private String file;

    private String acquirerRegistrationNo;

    private String acquirerName;

    private String internalVersion;

    private String description;

    private String msgType;

    private Date requestTime;
    
	public String toJsonString() {
        return new JSONSerializer().exclude("*.class").transform(new DateTransformer("ddMMyyyyHHmmss"), Date.class).serialize(this);
    }

    public FirmwareMessage fromJsonToMessage(String jsonString) {
        return new JSONDeserializer<FirmwareMessage>().use(Date.class, new DateTransformer("ddMMyyyyHHmmss")).use(null, FirmwareMessage.class).deserialize(jsonString);
    }
    

}

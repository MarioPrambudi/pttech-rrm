// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.domain.ReloadResponseMessage;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect ReloadResponseMessage_Roo_Json {
    
    public static String ReloadResponseMessage.toJsonArray(Collection<ReloadResponseMessage> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<ReloadResponseMessage> ReloadResponseMessage.fromJsonArrayToReloadResponseMessages(String json) {
        return new JSONDeserializer<List<ReloadResponseMessage>>().use(null, ArrayList.class).use("values", ReloadResponseMessage.class).deserialize(json);
    }
    
}

// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import java.lang.String;

privileged aspect Configuration_Roo_ToString {
    
    public String Configuration.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ConfigPrefix: ").append(getConfigPrefix()).append(", ");
        sb.append("ConfigKey: ").append(getConfigKey()).append(", ");
        sb.append("ConfigValue: ").append(getConfigValue()).append(", ");
        sb.append("Ordering: ").append(getOrdering());
        return sb.toString();
    }
    
}

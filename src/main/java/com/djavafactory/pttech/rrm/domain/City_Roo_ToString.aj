// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import java.lang.String;

privileged aspect City_Roo_ToString {
    
    public String City.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AcquirerState: ").append(getAcquirerState()).append(", ");
        sb.append("CityName: ").append(getCityName());
        return sb.toString();
    }
    
}

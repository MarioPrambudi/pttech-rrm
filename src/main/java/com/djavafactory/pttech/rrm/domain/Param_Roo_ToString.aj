// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import java.lang.String;

privileged aspect Param_Roo_ToString {
    
    public String Param.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CreatedBy: ").append(getCreatedBy()).append(", ");
        sb.append("CreatedTime: ").append(getCreatedTime()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("ParameterFile: ").append(java.util.Arrays.toString(getParameterFile())).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}

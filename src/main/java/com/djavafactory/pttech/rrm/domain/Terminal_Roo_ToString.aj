// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import java.lang.String;

privileged aspect Terminal_Roo_ToString {
    
    public String Terminal.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TerminalId: ").append(getTerminalId()).append(", ");
        sb.append("Ip: ").append(getIp()).append(", ");
        sb.append("Port: ").append(getPort()).append(", ");
        sb.append("Description: ").append(getDescription()).append(", ");
        sb.append("AcquirerState: ").append(getAcquirerState()).append(", ");
        sb.append("City: ").append(getCity()).append(", ");
        sb.append("Status: ").append(getStatus()).append(", ");
        sb.append("CreatedTime: ").append(getCreatedTime()).append(", ");
        sb.append("ModifiedTime: ").append(getModifiedTime()).append(", ");
        sb.append("CreatedBy: ").append(getCreatedBy()).append(", ");
        sb.append("ModifiedBy: ").append(getModifiedBy()).append(", ");
        sb.append("Acquirer: ").append(getAcquirer()).append(", ");
        sb.append("TerminalType: ").append(getTerminalType());
        return sb.toString();
    }
    
}

// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import java.lang.String;

privileged aspect SyncTerminalMessage_Roo_ToString {
    
    public String SyncTerminalMessage.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AcquirerEmail: ").append(getAcquirerEmail()).append(", ");
        sb.append("AcquirerHotline: ").append(getAcquirerHotline()).append(", ");
        sb.append("AcquirerName: ").append(getAcquirerName()).append(", ");
        sb.append("AcquirerRegistrationNo: ").append(getAcquirerRegistrationNo()).append(", ");
        sb.append("FirmwareFile: ").append(java.util.Arrays.toString(getFirmwareFile())).append(", ");
        sb.append("ParameterFile: ").append(java.util.Arrays.toString(getParameterFile())).append(", ");
        sb.append("RequestTime: ").append(getRequestTime()).append(", ");
        sb.append("TerminalId: ").append(getTerminalId()).append(", ");
        sb.append("TerminalStatus: ").append(getTerminalStatus());
        return sb.toString();
    }
    
}

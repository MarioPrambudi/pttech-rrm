// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import java.lang.String;

privileged aspect Firmware_Roo_ToString {
    
    public String Firmware.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Acquirer: ").append(getAcquirer()).append(", ");
        sb.append("Active: ").append(getActive()).append(", ");
        sb.append("FirmwareFile: ").append(java.util.Arrays.toString(getFirmwareFile())).append(", ");
        sb.append("Name: ").append(getName());
        return sb.toString();
    }
    
}

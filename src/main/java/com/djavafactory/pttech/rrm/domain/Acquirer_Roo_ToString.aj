// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import java.lang.String;

privileged aspect Acquirer_Roo_ToString {
    
    public String Acquirer.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AcquirerState: ").append(getAcquirerState()).append(", ");
        sb.append("City: ").append(getCity()).append(", ");
        sb.append("CityName: ").append(getCityName()).append(", ");
        sb.append("CreatedBy: ").append(getCreatedBy()).append(", ");
        sb.append("CreatedTime: ").append(getCreatedTime()).append(", ");
        sb.append("Deleted: ").append(getDeleted()).append(", ");
        sb.append("Email: ").append(getEmail()).append(", ");
        sb.append("Firmwares: ").append(getFirmwares() == null ? "null" : getFirmwares().size()).append(", ");
        sb.append("Hotline: ").append(getHotline()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("ModifiedBy: ").append(getModifiedBy()).append(", ");
        sb.append("ModifiedTime: ").append(getModifiedTime()).append(", ");
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("PostCode: ").append(getPostCode()).append(", ");
        sb.append("RegistrationNo: ").append(getRegistrationNo()).append(", ");
        sb.append("Street1: ").append(getStreet1()).append(", ");
        sb.append("Street2: ").append(getStreet2()).append(", ");
        sb.append("Terminals: ").append(getTerminals() == null ? "null" : getTerminals().size()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}

// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import java.lang.String;

privileged aspect ReloadRequest_Roo_ToString {
    
    public String ReloadRequest.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AcquirerTerminal: ").append(getAcquirerTerminal()).append(", ");
        sb.append("CmmpTrxId: ").append(getCmmpTrxId()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("MfgNumber: ").append(getMfgNumber()).append(", ");
        sb.append("MobileNo: ").append(getMobileNo()).append(", ");
        sb.append("ModifiedTime: ").append(getModifiedTime()).append(", ");
        sb.append("ReloadAmount: ").append(getReloadAmount()).append(", ");
        sb.append("RequestedTime: ").append(getRequestedTime()).append(", ");
        sb.append("ServiceProviderId: ").append(getServiceProviderId()).append(", ");
        sb.append("Status: ").append(getStatus()).append(", ");
        sb.append("TngKey: ").append(getTngKey()).append(", ");
        sb.append("TotalCancellationAmt: ").append(getTotalCancellationAmt()).append(", ");
        sb.append("TotalReloadQty: ").append(getTotalReloadQty()).append(", ");
        sb.append("TransCode: ").append(getTransCode()).append(", ");
        sb.append("TransId: ").append(getTransId()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}

// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.domain.Acquirer;
import com.djavafactory.pttech.rrm.domain.Province;
import com.djavafactory.pttech.rrm.domain.TerminalType;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

privileged aspect Terminal_Roo_JavaBean {
    
    public String Terminal.getTerminalId() {
        return this.terminalId;
    }
    
    public void Terminal.setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }
    
    public String Terminal.getIp() {
        return this.ip;
    }
    
    public void Terminal.setIp(String ip) {
        this.ip = ip;
    }
    
    public String Terminal.getPort() {
        return this.port;
    }
    
    public void Terminal.setPort(String port) {
        this.port = port;
    }
    
    public String Terminal.getDescription() {
        return this.description;
    }
    
    public void Terminal.setDescription(String description) {
        this.description = description;
    }
    
    public Province Terminal.getAcquirerState() {
        return this.acquirerState;
    }
    
    public void Terminal.setAcquirerState(Province acquirerState) {
        this.acquirerState = acquirerState;
    }
    
    public Long Terminal.getCity() {
        return this.city;
    }
    
    public void Terminal.setCity(Long city) {
        this.city = city;
    }
    
    public String Terminal.getLocation() {
        return this.location;
    }
    
    public void Terminal.setLocation(String location) {
        this.location = location;
    }
    
    public String Terminal.getStatus() {
        return this.status;
    }
    
    public void Terminal.setStatus(String status) {
        this.status = status;
    }
    
    public Date Terminal.getCreatedTime() {
        return this.createdTime;
    }
    
    public void Terminal.setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    
    public Date Terminal.getModifiedTime() {
        return this.modifiedTime;
    }
    
    public void Terminal.setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
    
    public String Terminal.getCreatedBy() {
        return this.createdBy;
    }
    
    public void Terminal.setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public String Terminal.getModifiedBy() {
        return this.modifiedBy;
    }
    
    public void Terminal.setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    
    public Acquirer Terminal.getAcquirer() {
        return this.acquirer;
    }
    
    public void Terminal.setAcquirer(Acquirer acquirer) {
        this.acquirer = acquirer;
    }
    
    public TerminalType Terminal.getTerminalType() {
        return this.terminalType;
    }
    
    public void Terminal.setTerminalType(TerminalType terminalType) {
        this.terminalType = terminalType;
    }
    
    public Date Terminal.getLastHeartBeatReceived() {
        return this.lastHeartBeatReceived;
    }
    
    public void Terminal.setLastHeartBeatReceived(Date lastHeartBeatReceived) {
        this.lastHeartBeatReceived = lastHeartBeatReceived;
    }
    
}

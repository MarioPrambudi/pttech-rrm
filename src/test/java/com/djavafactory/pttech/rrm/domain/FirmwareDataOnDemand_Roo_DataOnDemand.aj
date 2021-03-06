// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.domain.AcquirerDataOnDemand;
import com.djavafactory.pttech.rrm.domain.Firmware;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect FirmwareDataOnDemand_Roo_DataOnDemand {
    
    declare @type: FirmwareDataOnDemand: @Component;
    
    private Random FirmwareDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Firmware> FirmwareDataOnDemand.data;
    
    @Autowired
    private AcquirerDataOnDemand FirmwareDataOnDemand.acquirerDataOnDemand;
    
    public Firmware FirmwareDataOnDemand.getNewTransientFirmware(int index) {
        com.djavafactory.pttech.rrm.domain.Firmware obj = new com.djavafactory.pttech.rrm.domain.Firmware();
        setName(obj, index);
        setFirmwareFile(obj, index);
        setActive(obj, index);
        setAcquirer(obj, index);
        setCreatedBy(obj, index);
        setCreatedTime(obj, index);
        setVersionExt(obj, index);
        setVersionInt(obj, index);
        setDescription(obj, index);
        return obj;
    }
    
    public void FirmwareDataOnDemand.setName(Firmware obj, int index) {
        java.lang.String name = "name_" + index;
        obj.setName(name);
    }
    
    public void FirmwareDataOnDemand.setFirmwareFile(Firmware obj, int index) {
        byte[] firmwareFile = String.valueOf(index).getBytes();
        obj.setFirmwareFile(firmwareFile);
    }
    
    public void FirmwareDataOnDemand.setActive(Firmware obj, int index) {
        java.lang.Boolean active = Boolean.TRUE;
        obj.setActive(active);
    }
    
    public void FirmwareDataOnDemand.setAcquirer(Firmware obj, int index) {
        com.djavafactory.pttech.rrm.domain.Acquirer acquirer = acquirerDataOnDemand.getRandomAcquirer();
        obj.setAcquirer(acquirer);
    }
    
    public void FirmwareDataOnDemand.setCreatedBy(Firmware obj, int index) {
        java.lang.String createdBy = "createdBy_" + index;
        obj.setCreatedBy(createdBy);
    }
    
    public void FirmwareDataOnDemand.setCreatedTime(Firmware obj, int index) {
        java.util.Date createdTime = new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setCreatedTime(createdTime);
    }
    
    public void FirmwareDataOnDemand.setVersionExt(Firmware obj, int index) {
        java.lang.String versionExt = "versionExt_" + index;
        obj.setVersionExt(versionExt);
    }
    
    public void FirmwareDataOnDemand.setVersionInt(Firmware obj, int index) {
        java.lang.String versionInt = "versionInt_" + index;
        obj.setVersionInt(versionInt);
    }
    
    public void FirmwareDataOnDemand.setDescription(Firmware obj, int index) {
        java.lang.String description = "description_" + index;
        obj.setDescription(description);
    }
    
    public Firmware FirmwareDataOnDemand.getSpecificFirmware(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Firmware obj = data.get(index);
        return Firmware.findFirmware(obj.getId());
    }
    
    public Firmware FirmwareDataOnDemand.getRandomFirmware() {
        init();
        Firmware obj = data.get(rnd.nextInt(data.size()));
        return Firmware.findFirmware(obj.getId());
    }
    
    public boolean FirmwareDataOnDemand.modifyFirmware(Firmware obj) {
        return false;
    }
    
    public void FirmwareDataOnDemand.init() {
        data = com.djavafactory.pttech.rrm.domain.Firmware.findFirmwareEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Firmware' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.djavafactory.pttech.rrm.domain.Firmware>();
        for (int i = 0; i < 10; i++) {
            com.djavafactory.pttech.rrm.domain.Firmware obj = getNewTransientFirmware(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}

// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.domain.Acquirer;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect AcquirerDataOnDemand_Roo_DataOnDemand {
    
    declare @type: AcquirerDataOnDemand: @Component;
    
    private Random AcquirerDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Acquirer> AcquirerDataOnDemand.data;
    
    public Acquirer AcquirerDataOnDemand.getNewTransientAcquirer(int index) {
        com.djavafactory.pttech.rrm.domain.Acquirer obj = new com.djavafactory.pttech.rrm.domain.Acquirer();
        obj.setName("name_" + index);
        obj.setRegistrationNo("registrationNo_" + index);
        obj.setStreet1("street1_" + index);
        obj.setStreet2("street2_" + index);
        obj.setAcquirerState("acquirerState_" + index);
        obj.setCity("city_" + index);
        obj.setPostCode("postCode_" + index);
        obj.setEmail("email_" + index);
        obj.setHotline("hotline_" + index);
        obj.setCreatedBy("createdBy_" + index);
        obj.setModifiedBy("modifiedBy_" + index);
        obj.setCreatedTime(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        obj.setModifiedTime(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        return obj;
    }
    
    public Acquirer AcquirerDataOnDemand.getSpecificAcquirer(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Acquirer obj = data.get(index);
        return Acquirer.findAcquirer(obj.getId());
    }
    
    public Acquirer AcquirerDataOnDemand.getRandomAcquirer() {
        init();
        Acquirer obj = data.get(rnd.nextInt(data.size()));
        return Acquirer.findAcquirer(obj.getId());
    }
    
    public boolean AcquirerDataOnDemand.modifyAcquirer(Acquirer obj) {
        return false;
    }
    
    public void AcquirerDataOnDemand.init() {
        data = com.djavafactory.pttech.rrm.domain.Acquirer.findAcquirerEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Acquirer' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.djavafactory.pttech.rrm.domain.Acquirer>();
        for (int i = 0; i < 10; i++) {
            com.djavafactory.pttech.rrm.domain.Acquirer obj = getNewTransientAcquirer(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}

package com.djavafactory.pttech.rrm.domain;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Acquirer.class)
public class AcquirerDataOnDemand {

    public Acquirer getNewTransientAcquirer(int index) {
        com.djavafactory.pttech.rrm.domain.Province provinceObj = new com.djavafactory.pttech.rrm.domain.Province();
        provinceObj.setName("name_" + index);
        provinceObj.persist();
        provinceObj.flush();

        com.djavafactory.pttech.rrm.domain.Acquirer obj = new com.djavafactory.pttech.rrm.domain.Acquirer();
        obj.setName("name_" + index);
        obj.setRegistrationNo("registrationNo_" + index);
        obj.setStreet1("street1_" + index);
        obj.setStreet2("street2_" + index);
        obj.setAcquirerState(provinceObj);
        obj.setCity("city_" + index);
        obj.setPostCode("postCode_" + index);
        obj.setEmail("testuser@testemail.com");
        obj.setHotline("hotline_" + index);
        obj.setCreatedBy("createdBy_" + index);
        obj.setModifiedBy("modifiedBy_" + index);
        obj.setCreatedTime(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        obj.setModifiedTime(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        obj.setDeleted(Boolean.TRUE);
        return obj;
    }
}

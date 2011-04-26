package com.djavafactory.pttech.rrm.domain;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Terminal.class)
public class TerminalDataOnDemand {

    public Terminal getNewTransientTerminal(int index) {
        com.djavafactory.pttech.rrm.domain.Terminal obj = new com.djavafactory.pttech.rrm.domain.Terminal();
        obj.setTerminalId("terminalId_" + index);
        obj.setIp("ip_" + index);
        obj.setPort("port_" + index);
        obj.setDescription("description_" + index);
        obj.setAcquirerState(getProvince(index));
        obj.setCity("city_" + index);
        obj.setLocation("location_" + index);
        obj.setStatus("status_" + index);
        obj.setCreatedTime(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        obj.setModifiedTime(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        obj.setCreatedBy("createdBy_" + index);
        obj.setModifiedBy("modifiedBy_" + index);
        obj.setAcquirer(getAcquirer(index));
        obj.setTerminalType(getTerminalType(index));
        return obj;
    }

    private static com.djavafactory.pttech.rrm.domain.Acquirer getAcquirer(int index) {
        com.djavafactory.pttech.rrm.domain.Acquirer acquirerObj = new com.djavafactory.pttech.rrm.domain.Acquirer();
        acquirerObj.setName("name_" + index);
        acquirerObj.setRegistrationNo("registrationNo_" + index);
        acquirerObj.setStreet1("street1_" + index);
        acquirerObj.setStreet2("street2_" + index);
        acquirerObj.setAcquirerState(getProvince(index));
        acquirerObj.setCity(new Long(index));
        acquirerObj.setPostCode("postCode_" + index);
        acquirerObj.setEmail("testuser@testemail.com");
        acquirerObj.setHotline("hotline_" + index);
        acquirerObj.setCreatedBy("createdBy_" + index);
        acquirerObj.setModifiedBy("modifiedBy_" + index);
        acquirerObj.setCreatedTime(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        acquirerObj.setModifiedTime(new java.util.GregorianCalendar(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), java.util.Calendar.getInstance().get(java.util.Calendar.MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY), java.util.Calendar.getInstance().get(java.util.Calendar.MINUTE), java.util.Calendar.getInstance().get(java.util.Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime());
        acquirerObj.setDeleted(Boolean.TRUE);
        acquirerObj.persist();
        acquirerObj.flush();

        return acquirerObj;
    }

    private static com.djavafactory.pttech.rrm.domain.Province getProvince(int index) {
        com.djavafactory.pttech.rrm.domain.Province provinceObj = new com.djavafactory.pttech.rrm.domain.Province();
        provinceObj.setName("name_" + index);
        provinceObj.persist();
        provinceObj.flush();

        return provinceObj;
    }

    private static com.djavafactory.pttech.rrm.domain.TerminalType getTerminalType(int index) {
        com.djavafactory.pttech.rrm.domain.TerminalType terminalTypeObject = new com.djavafactory.pttech.rrm.domain.TerminalType();
        terminalTypeObject.setName("name_" + index);
        terminalTypeObject.setDescription("desc_" + index);
        terminalTypeObject.persist();
        terminalTypeObject.flush();

        return terminalTypeObject;
    }
}

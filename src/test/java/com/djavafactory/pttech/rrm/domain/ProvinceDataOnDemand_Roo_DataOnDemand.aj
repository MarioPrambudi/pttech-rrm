// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.domain.Province;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect ProvinceDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ProvinceDataOnDemand: @Component;
    
    private Random ProvinceDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Province> ProvinceDataOnDemand.data;
    
    public Province ProvinceDataOnDemand.getNewTransientProvince(int index) {
        com.djavafactory.pttech.rrm.domain.Province obj = new com.djavafactory.pttech.rrm.domain.Province();
        setName(obj, index);
        return obj;
    }
    
    public void ProvinceDataOnDemand.setName(Province obj, int index) {
        java.lang.String name = "name_" + index;
        obj.setName(name);
    }
    
    public Province ProvinceDataOnDemand.getSpecificProvince(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Province obj = data.get(index);
        return Province.findProvince(obj.getId());
    }
    
    public Province ProvinceDataOnDemand.getRandomProvince() {
        init();
        Province obj = data.get(rnd.nextInt(data.size()));
        return Province.findProvince(obj.getId());
    }
    
    public boolean ProvinceDataOnDemand.modifyProvince(Province obj) {
        return false;
    }
    
    public void ProvinceDataOnDemand.init() {
        data = com.djavafactory.pttech.rrm.domain.Province.findProvinceEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Province' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.djavafactory.pttech.rrm.domain.Province>();
        for (int i = 0; i < 10; i++) {
            com.djavafactory.pttech.rrm.domain.Province obj = getNewTransientProvince(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}

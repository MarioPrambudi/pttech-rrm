package com.djavafactory.pttech.rrm.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@RooJavaBean
@RooToString
@RooEntity
public class Province {

    private String name;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "acquirerState")
    private Set<City> cities = new HashSet<City>();

}

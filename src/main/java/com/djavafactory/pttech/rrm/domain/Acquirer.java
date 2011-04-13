package com.djavafactory.pttech.rrm.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import com.djavafactory.pttech.rrm.domain.Terminal;

@RooJavaBean
@RooToString
@RooEntity
public class Acquirer {

    private String name;

    private String registrationNo;

    private String street1;

    private String street2;

    private String acquirerState;

    private String city;

    private String postCode;

    private String email;

    private String hotline;

    private String createdBy;

    private String modifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date modifiedTime;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "acquirer")
    private Set<Firmware> firmwares = new HashSet<Firmware>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "acquirer")
    private Set<Terminal> terminals = new HashSet<Terminal>();
}

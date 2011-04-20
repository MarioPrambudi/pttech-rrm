package com.djavafactory.pttech.rrm.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import com.djavafactory.pttech.rrm.domain.Terminal;
import org.springframework.beans.factory.annotation.Value;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findAcquirersByCreatedTimeBetween" })
public class Acquirer {

    @NotNull
    private String name;

    @NotNull
    private String registrationNo;

    @NotNull
    private String street1;

    private String street2;

    @NotNull
    private String acquirerState;

    @NotNull
    private String city;

    @NotNull
    private String postCode;

    @NotNull
    @Email
    private String email;

    @NotNull
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

    @Value("0")
    private Integer deletedStatus;

	
}

package com.djavafactory.pttech.rrm.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RooJavaBean
@RooToString
@RooEntity
public class Acquirer {

    private String name;

    private String registrationNo;

    private String street1;

    private String street2;

    @NotNull
    @ManyToOne
    private Province acquirerState;

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

    private Boolean deleted;

    public static TypedQuery<Acquirer> findAcquirersByParam(String name, String registrationNo, int firstResult, int maxResults) {
        EntityManager em = new Acquirer().entityManager();
        TypedQuery<Acquirer> q = null;
        String query = "SELECT Acquirer FROM Acquirer AS acquirer WHERE acquirer.deleted = false";
        if (name != null && !name.equals("")) {
            query = new StringBuilder(query).append(" AND LOWER(acquirer.name) LIKE LOWER(:name)").toString();
        }
        if (registrationNo != null && !registrationNo.equals("")) {
            query = new StringBuilder(query).append(" AND LOWER(acquirer.registrationNo) LIKE LOWER(:registrationNo)").toString();
        }
        q = (firstResult > 0 && maxResults > 0) ? em.createQuery(query, Acquirer.class).setFirstResult(firstResult).setMaxResults(maxResults) : em.createQuery(query, Acquirer.class);
        if (name != null && !name.equals("")) {
            q.setParameter("name", "%" + name + "%");
        }
        if (registrationNo != null && !registrationNo.equals("")) {
            q.setParameter("registrationNo", "%" + registrationNo + "%");
        }
        return q;
    }
}

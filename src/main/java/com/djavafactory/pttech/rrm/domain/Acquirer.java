package com.djavafactory.pttech.rrm.domain;

import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Value;
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
	
    @NotNull
    private String name;

    @NotNull
    private String registrationNo;

    @NotNull
    private String street1;

    private String street2;

    @NotNull
    @ManyToOne
    private Province acquirerState;

    @NotNull
    private Long city;

    @NotNull
    private String postCode;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String hotline;

    @Column(updatable=false, insertable=true)
    private String createdBy;

    private String modifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    @Column(updatable=false, insertable=true)
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date modifiedTime;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "acquirer")
    private Set<Firmware> firmwares = new HashSet<Firmware>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "acquirer")
    private Set<Terminal> terminals = new HashSet<Terminal>();

    @Value("false")
    private Boolean deleted;

    @Transient
    public String getCityName() {
        return City.findCity(this.city).getCityName();
    }

    /**
     * To search acquirers by parameters
     *
     * @param name The acquirer name
     * @param registrationNo The Acquirer registration no
     * @param deleted True to show the deleted records
     * @param order The order of the search results
     * @param firstResult Start index of the records
     * @param maxResults  Maximum records to be fetched   @return List of acquirer
     */
    public static TypedQuery<Acquirer> findAcquirersByParam(String name, String registrationNo, Boolean deleted, String order, int firstResult, int maxResults) {
        EntityManager em = Acquirer.entityManager();
        TypedQuery<Acquirer> q = null;
        String query = "SELECT Acquirer FROM Acquirer AS acquirer WHERE acquirer.deleted = false";
        if (deleted != null && !deleted.equals("") && deleted == true) {
            query = new StringBuilder(query).append(" AND acquirer.deleted = :notDeleted").toString();
        }
        if (name != null && !name.equals("")) {
            query = new StringBuilder(query).append(" AND LOWER(acquirer.name) LIKE LOWER(:name)").toString();
        }
        if (registrationNo != null && !registrationNo.equals("")) {
            query = new StringBuilder(query).append(" AND LOWER(acquirer.registrationNo) LIKE LOWER(:registrationNo)").toString();
        }
        if (order != null && !order.equals("")) {
            query = new StringBuilder(query).append(" ORDER BY :order").toString();
        }
        q = (firstResult > 0 && maxResults > 0) ? em.createQuery(query, Acquirer.class).setFirstResult(firstResult).setMaxResults(maxResults) : em.createQuery(query, Acquirer.class);
        if (deleted != null && !deleted.equals("")) {
            q.setParameter("notDeleted", deleted);
        }
        if (name != null && !name.equals("")) {
            q.setParameter("name", "%" + name + "%");
        }
        if (registrationNo != null && !registrationNo.equals("")) {
            q.setParameter("registrationNo", "%" + registrationNo + "%");
        }
        if (order != null && !order.equals("")) {
            q.setParameter("order", order);
        }
        return q;
    }
}

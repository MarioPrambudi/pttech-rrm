package com.djavafactory.pttech.rrm.domain;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.djavafactory.pttech.rrm.Constants;

@RooJavaBean
@RooToString
@RooEntity
public class Firmware {

	@Column(updatable = false, insertable = true)
	private String name;
	
    @Lob
    @Basic(fetch = javax.persistence.FetchType.LAZY)
    @Column(updatable = false, insertable = true)
    private byte[] firmwareFile;

    @Value("true")
    private Boolean active;

    @NotNull
    @ManyToOne
    private Acquirer acquirer;
    
    @Column(updatable = false, insertable = true)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    @Column(updatable = false, insertable = true)
    private Date createdTime;
    
    @Column(updatable = false, insertable = true)
    private String versionExt;
    
    @Column(updatable = false, insertable = true)
    private String versionInt;
    
    @Column(updatable = false, insertable = true)
    private String description;

    @Transient
    private Long acquirerId;
    
	/**
     * To get all active firmware (active=true)
     * @param firstResult int 
     * @param maxResults int 
     * @return  TypedQuery<Firmware>
     */
    public static TypedQuery<Firmware> findFirmwaresByParam (int firstResult, int maxResults) {
        EntityManager em = Firmware.entityManager();
        TypedQuery<Firmware> q = null;
        String query = "SELECT Firmware FROM Firmware AS firmware WHERE Active is TRUE";
        q = (firstResult > -1 && maxResults > 0) ? em.createQuery(query, Firmware.class).setFirstResult(firstResult).setMaxResults(maxResults) : em.createQuery(query, Firmware.class);    
        return q;
    }
    
    /**
     * To get total number of active firmware  (active=true)
     * @param none
     * @return long
     */
    public static long countFirmwaresByParam () {
        EntityManager em = Firmware.entityManager();
        TypedQuery<Long> q = null;
        String query = "SELECT count(Firmware) FROM Firmware AS firmware WHERE Active is TRUE";
        q = em.createQuery(query, Long.class); 
        return q.getSingleResult();
    }

}

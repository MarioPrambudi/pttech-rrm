package com.djavafactory.pttech.rrm.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import ca.digitalface.jasperoo.RooJasperoo;

@RooJavaBean
@RooToString
@RooJasperoo
@RooEntity(finders = { "findReloadRequestsByTransId", "findReloadRequestsById", "findReloadRequestsByRequestedTimeBetween" })
public class ReloadRequest {

    @NotNull
    @Column(unique = true)
    private String transId;

    private String status;

    private Long mfgNumber;

    private BigDecimal reloadAmount;

    private String serviceProviderId;

    private Integer transCode;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date requestedTime;

    private String tngKey;



    public static List<ReloadRequest>findListReloadRequestsByRequestedTimeBetween(Date minRequestedTime, Date maxRequestedTime) {
        if (minRequestedTime == null) throw new IllegalArgumentException("The minRequestedTime argument is required");
        if (maxRequestedTime == null) maxRequestedTime = minRequestedTime;
        TypedQuery<ReloadRequest> q = entityManager().createQuery("SELECT ReloadRequest FROM ReloadRequest reloadrequest", ReloadRequest.class);
      //  TypedQuery<ReloadRequest> q = entityManager().createQuery("SELECT ReloadRequest FROM ReloadRequest reloadrequest WHERE reloadrequest.requestedTime BETWEEN :minRequestedTime AND :maxRequestedTime", ReloadRequest.class);
       // q.setParameter("minRequestedTime", minRequestedTime);
      //  q.setParameter("maxRequestedTime", maxRequestedTime);
        return q.getResultList();
    }

     public static TypedQuery<ReloadRequest> findReloadRequestsByRequestedTimeBetween(Date minRequestedTime, Date maxRequestedTime) {
        if (minRequestedTime == null) throw new IllegalArgumentException("The minRequestedTime argument is required");
        if (maxRequestedTime == null) throw new IllegalArgumentException("The minRequestedTime argument is required");
        EntityManager em = ReloadRequest.entityManager();
        TypedQuery<ReloadRequest> q = em.createQuery("SELECT ReloadRequest FROM ReloadRequest AS reloadrequest WHERE reloadrequest.requestedTime BETWEEN :minRequestedTime AND :maxRequestedTime", ReloadRequest.class);
        q.setParameter("minRequestedTime", minRequestedTime);
        q.setParameter("maxRequestedTime", maxRequestedTime);
        return q;
    }


}

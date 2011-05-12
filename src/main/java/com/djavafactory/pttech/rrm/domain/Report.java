package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.Constants;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
public class Report {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date sofRequestedDatetime;

    private Long id;

    private String transId;

    private String status;

    private Long mfgNumber;

    private String serviceProviderId;

    private Integer transCode;

    private BigDecimal reloadAmount;

    private BigDecimal fees;

    private BigDecimal totalChargeToCustomer;

    private BigDecimal commissionAmountDeductedBySof;

    private BigDecimal netPaymentToTng;

    private long totalReloadQty;

    private BigDecimal totalAmountRequestRm;

    private BigDecimal totalFees;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date reloadDate;

    private BigDecimal totalReloadAmountRm;

    private BigDecimal amountRefundedToCustomer;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateRefundedCustomer;

    private String cancellationStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateCancelRequest;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date requestedTime;

    private Long totalCancellationQty;

    private BigDecimal totalAmountCancelledRm;

    private BigDecimal totalRefundToCustomerRm;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date transactionDate;

    private BigDecimal grossPaymentToTngRm;

    private BigDecimal totalCancellationRm;

    private BigDecimal amountCreditedToTngRm;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateCreditedToTngAccount;

    private BigDecimal totalPaymentToTngRm;

    private String tngKey;
    
    //Added
    private BigDecimal sumTotalChargeToCustomer;
    
    private BigDecimal sumCommissionAmountDeductedBySof;
    
    private BigDecimal sumNetPaymentToTng;
    
    @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	private Date modifiedDate;

//    /**
//
//     */
//    public static TypedQuery<ReloadRequest> findReloadRequestsByRequestedTimeBetweenAndStatus(String name, String registrationNo, Boolean deleted, String order, int firstResult, int maxResults) {
//        EntityManager em = ReloadRequest.entityManager();
//        TypedQuery<ReloadRequest> q = null;
//        String query = "SELECT ReloadRequest " +
//					   "FROM ReloadRequest reloadrequest " +
//					   "WHERE reloadrequest.requestedTime BETWEEN :minRequestedTime AND :maxRequestedTime " +
//					   "AND LOWER(reloadrequest.status) IN (:statusList)";		
//        q = (firstResult > -1 && maxResults > 0) ? em.createQuery(query, ReloadRequest.class).setFirstResult(firstResult).setMaxResults(maxResults) : em.createQuery(query, ReloadRequest.class);
//        return q;
//    }

    
}

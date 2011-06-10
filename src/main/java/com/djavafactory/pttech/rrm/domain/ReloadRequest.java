package com.djavafactory.pttech.rrm.domain;

import ca.digitalface.jasperoo.RooJasperoo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.format.annotation.NumberFormat;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.util.DateUtil;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RooJavaBean
@RooToString
@RooJasperoo
@RooEntity(finders = {"findReloadRequestsByTransId", "findReloadRequestsByRequestedTimeBetween"})
public class ReloadRequest {
	@NotNull
	@Column(unique = true)
	private String transId;

	private String status;

	private Long mfgNumber;

	@NumberFormat(pattern="#,##0.00")
	private BigDecimal reloadAmount;

	private String serviceProviderId;

	private String transCode;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date requestedTime;

	private String tngKey;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date modifiedTime;
	
	private String acquirerTerminal;
	
	private String cmmpTrxId;
	
	private Long mobileNo;

	@Transient
	private Long totalReloadQty;
	
	@Transient
	private BigDecimal totalCancellationAmt;

	
	// <<<<<<<<<<<<<<<<<<<<<<<<< REPORT FINDER - START >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	/**
     * To get the reload request between requested time and status(for generating the report)
     * @param minRequestedTime Date 
     * @param maxRequestedTime Date 
     * @param listStatus List<String> 
     * @param firstResult int 
     * @param maxResults int 
     * @return  TypedQuery<ReloadRequest>
	  * @throws ParseException 
     */
    public static  TypedQuery<ReloadRequest>findDailyReloadRequests(Date minRequestedTime, Date maxRequestedTime) throws ParseException {		
    	TypedQuery<ReloadRequest> q = findReloadRequestsByRequestedTimeBetween(minRequestedTime, maxRequestedTime);
    	return q;
   }
    
    /**
     * To get the reload request between requested time
     *
     * @param minRequestedTime From date
     * @param maxRequestedTime To date
     * @return List Reload request in list
     */
    public static TypedQuery<ReloadRequest> findReloadRequestsByRequestedTimeBetween(Date minRequestedTime,
                                                                                     Date maxRequestedTime) {
        if (minRequestedTime == null)
            throw new IllegalArgumentException("The minRequestedTime argument is required");
        if (maxRequestedTime == null)
            throw new IllegalArgumentException("The minRequestedTime argument is required");
        EntityManager em = ReloadRequest.entityManager();
        TypedQuery<ReloadRequest> q = em
                .createQuery(
                        "SELECT ReloadRequest FROM ReloadRequest AS reloadrequest WHERE reloadrequest.requestedTime BETWEEN :minRequestedTime AND :maxRequestedTime",
                        ReloadRequest.class);
        q.setParameter("minRequestedTime", minRequestedTime);
        q.setParameter("maxRequestedTime", maxRequestedTime);
        return q;
    }
     
     /**
      * To get total number of reload request between requested time and status (for generating the report with paging)
      * @param minRequestedTime Date
      * @param maxRequestedTime Date
      * @param listStatus       List<String>
      * @return totalresult long
      * @throws ParseException
      */
     public static long totalReloadRequestsByRequestedTimeBetweenAndStatus(Date minRequestedTime, Date maxRequestedTime) throws ParseException {
         EntityManager em = ReloadRequest.entityManager();
         TypedQuery<Long> q = null;
         String query = "SELECT count(ReloadRequest) " +
                 "FROM ReloadRequest reloadrequest " +
                 "WHERE reloadrequest.requestedTime BETWEEN :minRequestedTime AND :maxRequestedTime";
         q = em.createQuery(query, Long.class);
         q.setParameter("minRequestedTime", minRequestedTime);
         q.setParameter("maxRequestedTime", maxRequestedTime);
         return q.getSingleResult();
     }
    
   
	// <<<<<<<<<<<<<<<<<<<<<<<<< REPORT FINDER - END >>>>>>>>>>>>>>>>>>>>>>>>>>>>>

     
    public static TypedQuery<ReloadRequest> findReloadRequestsByTransId(String transId) {
        if (transId == null || transId.length() == 0)
            throw new IllegalArgumentException("The transId argument is required");
        EntityManager em = ReloadRequest.entityManager();
        TypedQuery<ReloadRequest> q = em.createQuery(
                "SELECT ReloadRequest FROM ReloadRequest AS reloadrequest WHERE reloadrequest.transId = :transId",
                ReloadRequest.class);
        q.setParameter("transId", transId);
        return q;
    }

    public static Long countReloadRequestsByParam(String status, String serviceProviderId, Date requestedTimeFrom,
                                                  Date requestedTimeTo) {
        EntityManager em = ReloadRequest.entityManager();
        TypedQuery<Long> typedQuery = null;
        StringBuilder queryBuilder = buildWhereClause(new StringBuilder(
                "SELECT count(reloadrequest) FROM ReloadRequest reloadrequest WHERE 1=1 "), status, serviceProviderId,
                requestedTimeFrom, requestedTimeTo);
        typedQuery = em.createQuery(queryBuilder.toString(), Long.class);
        return (Long) putQueryParams(typedQuery, status, serviceProviderId, requestedTimeFrom, requestedTimeTo)
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public static TypedQuery<ReloadRequest> findReloadRequestsByParam(String status, String serviceProviderId,
                                                                      Date requestedTimeFrom, Date requestedTimeTo, int firstResult, int maxResults, String order) {
        EntityManager em = ReloadRequest.entityManager();
        TypedQuery<ReloadRequest> typedQuery = null;

        StringBuilder queryBuilder = new StringBuilder("SELECT ReloadRequest FROM ReloadRequest reloadrequest WHERE 1=1 ");

        if (status != null && !status.isEmpty() && !status.equalsIgnoreCase("-1"))
            queryBuilder.append("AND LOWER(reloadrequest.status) = LOWER(:status)");
        if (serviceProviderId != null && !serviceProviderId.isEmpty())
            queryBuilder.append("AND LOWER(reloadrequest.serviceProviderId) LIKE LOWER(:serviceProviderId)");
        if (requestedTimeFrom != null && requestedTimeTo == null)
            queryBuilder.append("AND reloadrequest.requestedTime >= :requestedTimeFrom");
        else if (requestedTimeFrom == null && requestedTimeTo != null)
            queryBuilder.append("AND reloadrequest.requestedTime <= :requestedTimeTo");
        else if (requestedTimeFrom != null && requestedTimeTo != null)
            queryBuilder.append("AND reloadrequest.requestedTime BETWEEN :requestedTimeFrom AND :requestedTimeTo");

        if (order != null && !order.isEmpty())
            queryBuilder.append(" ORDER BY " + order);

        String stringQuery = queryBuilder.toString();

        typedQuery = (firstResult >= 0 && maxResults > 0) ? em.createQuery(stringQuery, ReloadRequest.class)
                .setFirstResult(firstResult).setMaxResults(maxResults) : em.createQuery(stringQuery, ReloadRequest.class);

        return (TypedQuery<ReloadRequest>) putQueryParams(typedQuery, status, serviceProviderId, requestedTimeFrom,
                requestedTimeTo);
    }

    private static StringBuilder buildWhereClause(StringBuilder queryBuilder, String status, String serviceProviderId,
                                                  Date requestedTimeFrom, Date requestedTimeTo) {
        if (status != null && !status.isEmpty() && !status.equalsIgnoreCase("-1"))
            queryBuilder.append("AND LOWER(reloadrequest.status) = LOWER(:status)");
        if (serviceProviderId != null && !serviceProviderId.isEmpty())
            queryBuilder.append("AND LOWER(reloadrequest.serviceProviderId) LIKE LOWER(:serviceProviderId)");
        if (requestedTimeFrom != null && requestedTimeTo == null)
            queryBuilder.append("AND reloadrequest.requestedTime >= :requestedTimeFrom");
        else if (requestedTimeFrom == null && requestedTimeTo != null)
            queryBuilder.append("AND reloadrequest.requestedTime <= :requestedTimeTo");
        else if (requestedTimeFrom != null && requestedTimeTo != null)
            queryBuilder.append("AND reloadrequest.requestedTime BETWEEN :requestedTimeFrom AND :requestedTimeTo");
        return queryBuilder;
    }

    private static TypedQuery<?> putQueryParams(TypedQuery<?> typedQuery, String status, String serviceProviderId,
                                                Date requestedTimeFrom, Date requestedTimeTo) {
        if (status != null && !status.isEmpty() && !status.equalsIgnoreCase("-1"))
            typedQuery.setParameter("status", status);
        if (serviceProviderId != null && !serviceProviderId.isEmpty())
            typedQuery.setParameter("serviceProviderId", "%" + serviceProviderId + "%");
        if (requestedTimeFrom != null)
            typedQuery.setParameter("requestedTimeFrom", requestedTimeFrom);
        if (requestedTimeTo != null)
            typedQuery.setParameter("requestedTimeTo", requestedTimeTo);
        return typedQuery;
    }
 

    public static long totalReloadRequests(Date minRequestedTime, Date maxRequestedTime, List<String> listStatus)  throws ParseException {
    	  EntityManager em = ReloadRequest.entityManager();
          TypedQuery<Long> q = null;
          String query = "SELECT count(ReloadRequest) " +
                  "FROM ReloadRequest reloadrequest " +
                  "WHERE reloadrequest.requestedTime BETWEEN :minRequestedTime AND :maxRequestedTime " +
                  "AND LOWER(reloadrequest.status) IN (:statusList)";
          q = em.createQuery(query, Long.class);
          q.setParameter("minRequestedTime", minRequestedTime);
          q.setParameter("maxRequestedTime", maxRequestedTime);
          q.setParameter("statusList", listStatus);
          return q.getSingleResult();
    }
}

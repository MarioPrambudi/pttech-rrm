package com.djavafactory.pttech.rrm.domain;

import ca.digitalface.jasperoo.RooJasperoo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.djavafactory.pttech.rrm.Constants;
import com.djavafactory.pttech.rrm.util.DateUtil;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

	public static TypedQuery<ReloadRequest> findReloadRequestsByParam(String status, String serviceProviderId,
			Date requestedTimeFrom, Date requestedTimeTo, int firstResult, int maxResults, String order) {
		EntityManager em = ReloadRequest.entityManager();
		TypedQuery<ReloadRequest> typedQuery = null;
		StringBuilder queryBuilder = new StringBuilder("SELECT ReloadRequest FROM ReloadRequest AS reloadrequest WHERE 1=1 ");
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
		typedQuery = (firstResult > 0 && maxResults > 0) ? em.createQuery(stringQuery, ReloadRequest.class)
				.setFirstResult(firstResult).setMaxResults(maxResults) : em.createQuery(stringQuery, ReloadRequest.class);
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



     
     

 	/**
      * To get the reload request between requested time
      * @param status The reload request status
      * @return List Reload request in list
 	  * @throws ParseException 
      */
     public static List<ReloadRequest>findDailyReloadRequestsByRequestedTimeBetween(String status) throws ParseException {		
 		
    	Date dateMin = null;
    	Date dateMax = null;   	 
		dateMin = DateUtil.getCurrentDate("dd/MM/yyyy");
		dateMax = DateUtil.add(dateMin, 5, 1); 
		String query = "SELECT ReloadRequest FROM ReloadRequest reloadrequest WHERE reloadrequest.requestedTime BETWEEN :minRequestedTime AND :maxRequestedTime";		
		
		if (!(Constants.RELOAD_REQUEST_ALL.equals(status)))
		{
			if(Constants.RELOAD_REQUEST_ALLFAIL.equals(status))
			{
				query = new StringBuilder(query).append(" AND LOWER(reloadrequest.status) = LOWER(:statusFail)").toString();
				query = new StringBuilder(query).append(" OR LOWER(reloadrequest.status) = LOWER(:statusExpire)").toString();
				query = new StringBuilder(query).append(" OR LOWER(reloadrequest.status) = LOWER(:statusCancel)").toString();
			}			
			if(Constants.RELOAD_STATUS_SUCCESS.equals(status))
			{
				query = new StringBuilder(query).append(" AND LOWER(reloadrequest.status) = LOWER(:status)").toString();
			}					
		}
		
		TypedQuery<ReloadRequest> q = entityManager().createQuery(query, ReloadRequest.class);
		q.setParameter("minRequestedTime",  dateMin);
		q.setParameter("maxRequestedTime",  dateMax);			
		if (!(Constants.RELOAD_REQUEST_ALL.equals(status)))
		{
			if(Constants.RELOAD_REQUEST_ALLFAIL.equals(status))
			{
				q.setParameter(":statusFail",  Constants.RELOAD_REQUEST_FAILED);
				q.setParameter(":statusExpire",  Constants.RELOAD_REQUEST_EXPIRED);
				q.setParameter(":statusCancel",  Constants.RELOAD_REQUEST_MANUALCANCEL);				
			}			
			if(Constants.RELOAD_STATUS_SUCCESS.equals(status))
			{
				q.setParameter(":status",  status);
			}
		}
	   return q.getResultList();        
     } 

	/**
	 * To get the reload request between requested time
	 * 
	 * @param minRequestedTime
	 *            From date
	 * @param maxRequestedTime
	 *            To date
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

}

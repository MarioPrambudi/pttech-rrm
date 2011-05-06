package com.djavafactory.pttech.rrm.domain;

import ca.digitalface.jasperoo.RooJasperoo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;


@RooJavaBean
@RooToString
@RooJasperoo
@RooEntity(finders = { "findReloadRequestsByTransId", "findReloadRequestsByRequestedTimeBetween" })
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

	public static TypedQuery<ReloadRequest> findReloadRequestsByParam(String status, int firstResult, int maxResults,
			String order) {
		EntityManager em = ReloadRequest.entityManager();
		TypedQuery<ReloadRequest> typedQuery = null;
		String stringQuery = "SELECT ReloadRequest FROM ReloadRequest AS reloadrequest";
		if (status != null && !status.isEmpty() && !status.equals("-1"))
			stringQuery = new StringBuilder(stringQuery).append(" WHERE LOWER(reloadrequest.status) = LOWER(:status)")
					.toString();
		if (order != null && !order.isEmpty())
			stringQuery = new StringBuilder(stringQuery).append(" ORDER BY ").append(order).toString();
		typedQuery = (firstResult > 0 && maxResults > 0) ? em.createQuery(stringQuery, ReloadRequest.class)
				.setFirstResult(firstResult).setMaxResults(maxResults) : em.createQuery(stringQuery, ReloadRequest.class);
		if (status != null && !status.isEmpty() && !status.equals("-1"))
			typedQuery.setParameter("status", status);
		return typedQuery;
	}


	/**
     * To get the reload request between requested time
     * @param minRequestedTime From date
     * @param maxRequestedTime To date
     * @return List Reload request in list
     */
    public static List<ReloadRequest>findListReloadRequestsByRequestedTimeBetween(Date minRequestedTime, Date maxRequestedTime) {
        if (minRequestedTime == null) throw new IllegalArgumentException("The minRequestedTime argument is required");
        if (maxRequestedTime == null) maxRequestedTime = minRequestedTime;
        TypedQuery<ReloadRequest> q = entityManager().createQuery("SELECT ReloadRequest FROM ReloadRequest reloadrequest", ReloadRequest.class);
      // TypedQuery<ReloadRequest> q = entityManager().createQuery("SELECT ReloadRequest FROM ReloadRequest reloadrequest WHERE reloadrequest.requestedTime BETWEEN :minRequestedTime AND :maxRequestedTime", ReloadRequest.class);
      // use DateUtil to change date format
      // q.setParameter("minRequestedTime", minRequestedTime);
      // q.setParameter("maxRequestedTime", maxRequestedTime);
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

package com.djavafactory.pttech.rrm.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;

import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooEntity
public class ConfValidityPeriod {

	@Column(updatable = false, insertable = true)
    private String configKey;

	@Column(updatable = false, insertable = true)
    private String configValue;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    @Column(updatable = false, insertable = true)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date endDate;
    
	@Transient
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy")
	private Date startDateNew;
	
	@Transient
	private String configValueHidden;
	
	/**
     * To find ConfValidityPeriod by configKey
     * @param configKey String
     * @return  ConfValidityPeriod 
	 * @throws IllegalArgumentException
     */
   public static ConfValidityPeriod findConfValidityPeriodByConfigKey(String configKey, Date requestedDate) {
        if (configKey == null || configKey.length() == 0)
            throw new IllegalArgumentException("The configKey argument is required");
        EntityManager em = ConfValidityPeriod.entityManager();
        TypedQuery<ConfValidityPeriod> q = em.createQuery("SELECT ConfValidityPeriod FROM ConfValidityPeriod AS confvalidityperiod WHERE (confvalidityperiod.configKey = :configKey and :requestedDate between startDate and endDate) or " + 
        		"(confvalidityperiod.configKey = :configKey and :requestedDate >= startDate and endDate is NULL)", ConfValidityPeriod.class);
        q.setParameter("configKey", configKey);
        q.setParameter("requestedDate", requestedDate);
        List<ConfValidityPeriod> confvalidityperiod = q.getResultList();
        return (confvalidityperiod != null && !confvalidityperiod.isEmpty()) ? confvalidityperiod.get(0) : null;
    }
   
   /**
    * To get a specific config value
    * @param valueName Config Key eg: REPORT.FEE
    * @return String Config value
    */
    public static String getReportConfigValue(String valueName, Date requestedDate){
    	ConfValidityPeriod config = findConfValidityPeriodByConfigKey(valueName, requestedDate);
          return config.getConfigValue().toString();
    }
}

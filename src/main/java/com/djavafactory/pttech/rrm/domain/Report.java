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
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@RooJavaBean
@RooToString
public class Report {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date sofRequestedDatetime;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date sofRequestedDate;

    private Long id;

    private String transId;

    private String status;

    private Long mfgNumber;

    private String serviceProviderId;

    private Integer transCode;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal reloadAmount;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal fees;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal totalChargeToCustomer;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal commissionAmountDeductedBySof;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal netPaymentToTng;

    private long totalReloadQty;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal totalAmountRequestRm;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal totalFees;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date reloadDateTime;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date reloadDate;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal totalReloadAmountRm;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal amountRefundedToCustomer;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateRefundedCustomer;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date dateTimeRefundedCustomer;
    
    private String cancellationStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateCancelRequest;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date requestedTime;

    private Long totalCancellationQty;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal totalAmountCancelledRm;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal totalRefundToCustomerRm;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date transactionDate;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal grossPaymentToTngRm;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal totalCancellationRm;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal amountCreditedToTngRm;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateCreditedToTngAccount;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date dateTimeCreditedToTngAccount;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal totalPaymentToTngRm;

    private String tngKey;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal sumTotalChargeToCustomer;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal sumCommissionAmountDeductedBySof;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal sumNetPaymentToTng;
    
    @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	private Date modifiedDate;
    
    private Long cmmpTrxId;
    
    private Long tngTrxId;
    
    private Long aircashAccNo;
    
    private Long mobileNo;
    
    private Long tngMfgNo;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal grossAmount;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal tngFee;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal printisFee;

    @NumberFormat(pattern="#,##0.00")
    private BigDecimal celcomMobileFee;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal cmmFee;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal amountDueTng;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal amountDuePrintis;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal amountDueCelcomMobile;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal amountDueCmm;
    
    @NumberFormat(pattern="#,##0.00")
    private BigDecimal totalGrossAmount;

}

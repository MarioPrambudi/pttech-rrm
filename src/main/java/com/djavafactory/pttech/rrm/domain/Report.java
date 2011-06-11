package com.djavafactory.pttech.rrm.domain;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import java.util.Date;


import org.springframework.data.annotation.Id;
import org.springframework.data.document.mongodb.index.Indexed;
import org.springframework.data.document.mongodb.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.NumberFormat;


@Document(collection = "Report")
@RooJavaBean
@RooToString
public class Report implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    
    @Id
    private ObjectId id;
    
    @NumberFormat(pattern="#,##0.00")
    private double rs;
    
    @NumberFormat(pattern="#,##0.00")
    private double at;
    
    @NumberFormat(pattern="#,##0.00")
    private double sof;
    
    @NumberFormat(pattern="#,##0.00")
    private double tng;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date requestedTime;
    
    @NumberFormat(pattern="#,##0.00")
    private double reloadAmount;
    
    @NumberFormat(pattern="#,##0.00")
    private double fees;
	
    @NumberFormat(pattern="#,##0.00")
    private double totalChargeToCustomer;
    
    private Long mfgNumber;
	
    @NumberFormat(pattern="#,##0.00")
    private double commissionAmountDeductedBySof;
    
    @NumberFormat(pattern="#,##0.00")
    private double netPaymentToTng;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date reloadDate;
    
    @NumberFormat(pattern="#,##0.00")
    private double amountRefundedToCustomer;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date dateRefundedCustomer;
    
    @NumberFormat(pattern="#,##0.00")
    private double grossPaymentToTngRm;
    
    private String acquirerTerminal;
    
    private String cmmpTrxId;
    
    private Long aircashAccNo;
    
    private Long mobileNo;
    
    @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date modifiedDate;

    private String transId;

    private String status;
  
    @NumberFormat(pattern="#,##0.00")
    private double tngFee;
    
    @NumberFormat(pattern="#,##0.00")
    private double printisFee;

    @NumberFormat(pattern="#,##0.00")
    private double celcomMobileFee;
    
    @NumberFormat(pattern="#,##0.00")
    private double cmmFee;
   
    @NumberFormat(pattern="#,##0.00")
    private double totalFees;

    @NumberFormat(pattern="#,##0.00")
    private double amountDueTng;
    
    @NumberFormat(pattern="#,##0.00")
    private double amountDuePrintis;
    
    @NumberFormat(pattern="#,##0.00")
    private double amountDueCelcomMobile;
    
    @NumberFormat(pattern="#,##0.00")
    private double amountDueCmm;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date dateCreditedToTngAccount;
//    
//    
//   //Transient field for displayed only 
// 
//	
	@Transient
	private Integer seqNo;
	
	@Transient
	private long totalReloadQty;
	
	@Transient
	@NumberFormat(pattern="#,##0.00")
    private double totalPaymentToTngRm;
    
	@Transient
	@NumberFormat(pattern="#,##0.00")
  	private double totalCancellationRm;
	
	@Transient
	@NumberFormat(pattern="#,##0.00")
  	private double sumNetPaymentToTng;
	
	@Transient
    @NumberFormat(pattern="#,##0.00")
    private double sumTotalChargeToCustomer;
  
	@Transient
    @NumberFormat(pattern="#,##0.00")
    private double sumCommissionAmountDeductedBySof;
	
	@Transient
	@NumberFormat(pattern="#,##0.00")
	private double totalAmountRequestRm;
	
	@Transient
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date dateCancelRequest;
   
	@Transient
	private Long totalCancellationQty;
	
	@Transient
	@NumberFormat(pattern="#,##0.00")
	private double totalRefundToCustomerRm;
	
	@Transient
	@NumberFormat(pattern="#,##0.00")
	private double commSofAmountComm;
	
	@Transient
	@NumberFormat(pattern="#,##0.00")
	private double sumTng;
	
	@Transient
	@NumberFormat(pattern="#,##0.00")
	private double sumCelcomMobile;
	
	@Transient
	@NumberFormat(pattern="#,##0.00")
	private double sumCmm;
	
	@Transient
	@NumberFormat(pattern="#,##0.00")
	private double sumTotalFee;

    
}

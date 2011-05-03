package com.djavafactory.pttech.rrm.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
public class Report {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date sofRequestedDatetime;

    private Long mfgNo;

    private Float requestedReloadAmtRm;

    private Float fees;

    private Float totalChargeToCustomer;

    private Float commissionAmountDeductedBySof;

    private Float netPaymentToTng;

    private long totalReloadQty;

    private Float totalAmountRequestRm;

    private Float totalFees;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date reloadDate;

    private Float reloadAmountRm;

    private Float totalReloadAmountRm;

    private Float amountRefundedToCustomer;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateRefundedCustomer;

    private String cancellationStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateCancelRequest;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateReloadRequest;

    private Long totalCancellationQty;

    private Float totalAmountCancelledRm;

    private Float totalRefundToCustomerRm;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date transactionDate;

    private Float grossPaymentToTngRm;

    private Float totalCancellationRm;

    private Float amountCreditedToTngRm;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date dateCreditedToTngAccount;

    private Float totalPaymentToTngRm;
}

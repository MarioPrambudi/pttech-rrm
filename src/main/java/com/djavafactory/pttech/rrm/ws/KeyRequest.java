//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.06 at 10:07:18 AM MYT 
//


package com.djavafactory.pttech.rrm.ws;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * <p>Java class for anonymous complex type.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "KeyRequest")
public class KeyRequest {

    @XmlElement(name = "TransactionId", required = true)
    protected String transactionId;
    @XmlElement(name = "MfgNo", required = true)
    protected Long mfgNo;
    @XmlElement(name = "Amount", required = true)
    protected BigDecimal amount;
    @XmlElement(name = "EncryptedMessage", required = true)
    protected String encryptedMessage;
    @XmlElement(name = "RequestDateTime", required = true)
    protected Date requestDateTime;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getMfgNo() {
        return mfgNo;
    }

    public void setMfgNo(Long mfgNo) {
        this.mfgNo = mfgNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }

    public void setEncryptedMessage(String encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }

    public Date getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(Date requestDateTime) {
        this.requestDateTime = requestDateTime;
    }
}

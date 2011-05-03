package com.djavafactory.pttech.rrm.service;


/**
 * The entry point for Batch Report to generate the MessagingGateway proxy.
 *
 * @author Carine Leong
 */
public interface BatchReportServiceManager {

    /**
     * Method to generate a MessagingGateway proxy for sending the Celcom Batch Report via FTPS.
     * The gateway then passes the batch report string as the payload of a
     * {@link org.springframework.integration.Message} to the
     * configured <em>requestChannel</em>.
     */
    public void sendCelcomBatchReport(String batchString);

    public void getCelcomBatchReportDetail();

    public void getTngBatchReportDetail();

    public void generateCelcomBatchReport();

    public void generateTngBatchReport();

}

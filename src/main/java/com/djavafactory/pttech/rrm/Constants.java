package com.djavafactory.pttech.rrm;


public final class Constants {

    public Constants() {
    }

    /**
     * Constants to be used in messaging to identify the type of the message.
     */
    public static final String RELOAD_REQUEST_NEW = "N";
    public static final String RELOAD_REQUEST_FAILED = "F";
    public static final String RELOAD_REQUEST_EXPIRED = "E";
    public static final String RELOAD_REQUEST_MANUALCANCEL = "M";
    public static final String RELOAD_REQUEST_SUCCESS = "S";

    /**
     * Constants to be used in database logging to identify the status of the transaction.
     */
    public static final String RELOAD_STATUS_NEW = "N";
    public static final String RELOAD_STATUS_PENDING = "P";
    public static final String RELOAD_STATUS_FAILED = "F";
    public static final String RELOAD_STATUS_EXPIRED = "E";
    public static final String RELOAD_STATUS_MANUALCANCEL = "C";
    public static final String RELOAD_STATUS_SUCCESS = "S";

    public static final String RESPONSE_CODE_SUCCESS = "00";

    /**
     * Constants for Configuration.
     */
    public static final String CONFIG_TNG_TIMEOUT = "RRM.timeout";
    public static final String CONFIG_CEL_BATCH_SCHEDULE = "CEL.uploadschedule";
    public static final String CONFIG_TNG_BATCH_SCHEDULE = "TNG.uploadschedule";
    public static final String CONFIG_CEL_LOCATION = "CEL.location";

    /**
     * Constants to be used in database logging to identify the status of the terminal.
     */
    public static final String TERMINAL_STATUS_ACTIVE = "A";
    public static final String TERMINAL_STATUS_INACTIVE = "X";
    public static final String TERMINAL_STATUS_DELETED = "D";
    public static final String TERMINAL_STATUS_BLOCK = "B";

    public static final String REPORT_CONFIG_FEE = "REPORT.FEES";
    public static final String REPORT_CONFIG_CELCOMM = "REPORT.CELCOMM";
    public static final String REPORT_CONFIG_TNGCOMM = "REPORT.TNGCOMM";


}

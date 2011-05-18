package com.djavafactory.pttech.rrm.exception;

/**
 * Enum that contains all error codes.
 *
 * @author Carine
 */
public enum RrmStatusCode {
    STS_SUCCESS("00", "SUCCESS"),
    STS_INVALIDMSGTYPE("01", "Invalid reload request message type"),
    STS_MSGTIMEOUT("02", "Message Timeout"),
    STS_INVALIDSTATUS("03", "Unable to proceed the request due to the record is not found or the record is not in a correct status"),
    STS_INVALIDLENGTH("04", "Value exceeded allowable length {0}"),
    STS_INVALIDNUMBERFORMAT("05", "Invalid amount format"),
    STS_GENERALRRMERROR("99","General RRM-specific error");

    private final String description;
    private final String code;

    private RrmStatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return code;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Gets the code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }
}

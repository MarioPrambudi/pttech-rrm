package com.djavafactory.pttech.rrm.exception;


import java.text.MessageFormat;

/**
 * Exceptions that are related to the business case.
 * Each exception has an error code and a message describing the error.
 *
 * @author Carine
 */

public class RrmBusinessException extends Exception {

    private String errorCode = null;
    private String[] args = null;
    private static final long serialVersionUID = -3182919753766245313L;

    /**
     * Instantiates a new acdd business exception.
     *
     * @param errorCode the error code
     * @param message   the message
     */
    public RrmBusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Instantiates a new RRM business exception.
     *
     * @param e the e
     */
    public RrmBusinessException(RrmStatusCode e) {
        super(e.getDescription());
        this.errorCode = e.getCode();
    }


    /**
     * Instantiates a new RRm business exception.
     *
     * @param errorCode the error code
     * @param message   the message
     * @param args      the args
     */
    public RrmBusinessException(String errorCode, String message, String... args) {
        super(message);
        this.errorCode = errorCode;
        this.args = args;
    }

    /**
     * Instantiates a new RRM business exception.
     *
     * @param errorCode the error code
     * @param message   the message
     * @param cause     the cause
     */
    public RrmBusinessException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Instantiates a new RRM business exception.
     *
     * @param errorCode the error code
     * @param message   the message
     * @param cause     the cause
     * @param args      the args
     */
    public RrmBusinessException(String errorCode, String message, Throwable cause, String... args) {
        super(message, cause);
        this.errorCode = errorCode;
        this.args = args;
    }

    /**
     * Gets the error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the error code.
     *
     * @param errorCode the new error code
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Gets the args.
     *
     * @return the args
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Sets the args.
     *
     * @param args the new args
     */
    public void setArgs(String[] args) {
        this.args = args;
    }

    /**
     * Gets the message.
     *
     * @param format the format
     * @return the message
     */
    public String getMessage(String format) {
        return MessageFormat.format(format, this.args);
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        if (this.args == null) {
            return this.errorCode + ": " + super.getMessage();
        } else {
            return this.errorCode + ": " + MessageFormat.format(super.getMessage(), this.args);
        }
    }
}

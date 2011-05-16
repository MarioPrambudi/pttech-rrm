package com.djavafactory.pttech.rrm.exception;

/**
 * 
 * Superclass for RRM exceptions
 * 
 * May 16, 2011 2:45:14 PM
 * @author Mario Tinton Prambudi
 *
 */
public class RrmException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5984140765007965250L;
	private String errorCode;
	private String message;

	public RrmException() {
		this.errorCode = RrmStatusCode.STS_GENERALRRMERROR.getCode();
		this.message = RrmStatusCode.STS_GENERALRRMERROR.getDescription();
	}

	public RrmException(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public RrmException(String message) {
		super(message);
	}

	public RrmException(String message, Throwable cause) {
		super(message, cause);
	}

	public RrmException(Throwable cause) {
		super(cause);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

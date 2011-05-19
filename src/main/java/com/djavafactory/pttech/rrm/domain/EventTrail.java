package com.djavafactory.pttech.rrm.domain;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.document.mongodb.index.Indexed;
import org.springframework.data.document.mongodb.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * 
 * May 13, 2011 5:34:20 PM
 * @author Mario Tinton Prambudi
 *
 */
@Document(collection = "EventTrail")
@RooJavaBean
@RooToString
public class EventTrail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2326588854852436309L;
	@Id
	private ObjectId id;
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date date;
	@Indexed
	private String code;
	private String message;
	private String source;
	private String user;
	private String stackTrace;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((stackTrace == null) ? 0 : stackTrace.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventTrail other = (EventTrail) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (stackTrace == null) {
			if (other.stackTrace != null)
				return false;
		} else if (!stackTrace.equals(other.stackTrace))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}

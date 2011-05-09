package com.djavafactory.pttech.rrm.domain;

import java.io.Serializable;
import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.document.mongodb.index.Indexed;
import org.springframework.data.document.mongodb.mapping.Document;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * 
 * @author Mario Tinton Prambudi
 * May 6, 2011 4:55:36 PM
 * 
 */
@Document(collection="AuditTrail")
@RooJavaBean
@RooToString
public class AuditTrail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5250184186459226353L;
	@Id
	private ObjectId id;
	private String entity;
	private String action;
	@Indexed
	private Date performedAt;
	private Object performedBy;
	private String description;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((performedAt == null) ? 0 : performedAt.hashCode());
		result = prime * result + ((performedBy == null) ? 0 : performedBy.hashCode());
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
		AuditTrail other = (AuditTrail) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (performedAt == null) {
			if (other.performedAt != null)
				return false;
		} else if (!performedAt.equals(other.performedAt))
			return false;
		if (performedBy == null) {
			if (other.performedBy != null)
				return false;
		} else if (!performedBy.equals(other.performedBy))
			return false;
		return true;
	}

}

package com.djavafactory.pttech.rrm.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@RooJavaBean
@RooToString
@RooEntity
public class Terminal {

    private String terminalId;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    @NotNull
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date modifiedTime;

    private String createdBy;

    private String modifiedBy;

    @NotNull
    @ManyToOne
    private Acquirer acquirer;

    public static TypedQuery<Terminal> findTerminalsByParam(String terminalId, String status, int firstResult, int maxResults) {
        EntityManager em = new Terminal().entityManager();
        TypedQuery<Terminal> q = null;
        String query = "SELECT Terminal FROM Terminal AS terminal WHERE terminal.status != 'd'";
        if (status != null && !status.equals("")) {
            query = new StringBuilder(query).append(" AND terminal.status = :status").toString();
        }
        if (terminalId != null && !terminalId.equals("")) {
            query = new StringBuilder(query).append(" AND LOWER(terminal.terminalId) LIKE LOWER(:terminalId)").toString();
        }
        q = (firstResult > 0 && maxResults > 0) ? em.createQuery(query, Terminal.class).setFirstResult(firstResult).setMaxResults(maxResults) : em.createQuery(query, Terminal.class);
        if (status != null && !status.equals("")) {
            q.setParameter("status", status);
        }
        if (terminalId != null && !terminalId.equals("")) {
            q.setParameter("terminalId", "%" + terminalId + "%");
        }
        return q;
    }
}

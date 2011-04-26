package com.djavafactory.pttech.rrm.domain;

import org.springframework.beans.factory.annotation.Value;
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

    @NotNull
    private String terminalId;

    @NotNull
    private String ip;

    @NotNull
    private String port;

    private String description;

    @NotNull
    @ManyToOne
    private Province acquirerState;

    @NotNull
    private String city;

    @NotNull
    private String location;

    @Value("X")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    @Column(updatable=false, insertable=true)
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date modifiedTime;

    @Column(updatable=false, insertable=true)
    private String createdBy;

    private String modifiedBy;

    @NotNull
    @ManyToOne
    private Acquirer acquirer;

    @NotNull
    @ManyToOne
    private TerminalType terminalType;

    /**
    * To search terminals by parameters
    * @param terminalId The terminal id
    * @param status The terminal status
    * @param firstResult Start index of the records
    * @param maxResults  Maximum records to be fetched
    * @exception none
    * @return List of terminal
    */
    public static TypedQuery<Terminal> findTerminalsByParam(String terminalId, String status, Long terminalType, Long acquirer, int firstResult, int maxResults) {
        EntityManager em = Terminal.entityManager();
        TypedQuery<Terminal> q = null;
        String query = "SELECT Terminal FROM Terminal AS terminal WHERE terminal.status != 'D'";
        if (status != null && !status.equals("") && !status.equals("-1")) {
            query = new StringBuilder(query).append(" AND terminal.status = :status").toString();
        }
        if (terminalId != null && !terminalId.equals("")) {
            query = new StringBuilder(query).append(" AND LOWER(terminal.terminalId) LIKE LOWER(:terminalId)").toString();
        }
        if (terminalType > 0L) {
            query = new StringBuilder(query).append(" AND LOWER(terminal.terminalType.id) = :terminalType").toString();
        }
        if (acquirer > 0L) {
            query = new StringBuilder(query).append(" AND LOWER(terminal.acquirer.id) = :acquirer").toString();
        }

        q = (firstResult > 0 && maxResults > 0) ? em.createQuery(query, Terminal.class).setFirstResult(firstResult).setMaxResults(maxResults) : em.createQuery(query, Terminal.class);
        if (status != null && !status.equals("") && !status.equals("-1")) {
            q.setParameter("status", status);
        }
        if (terminalId != null && !terminalId.equals("")) {
            q.setParameter("terminalId", "%" + terminalId + "%");
        }
        if (terminalType > 0L) {
            q.setParameter("terminalType", terminalType);
        }
        if (acquirer > 0L) {
            q.setParameter("acquirer", acquirer);
        }
        return q;
    }
}

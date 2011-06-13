package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@RooJavaBean
@RooToString
@RooEntity
public class Terminal {

    @NotNull
    @Column(unique = true)
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
    private Long city;

    @NotNull
    private String location;

    @Value("X")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    @Column(updatable = false, insertable = true)
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date modifiedTime;

    @Column(updatable = false, insertable = true)
    private String createdBy;

    private String modifiedBy;

    @NotNull
    @ManyToOne
    private Acquirer acquirer;

    @NotNull
    @ManyToOne
    private TerminalType terminalType;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date lastHeartBeatReceived;

    @Transient
    public String getCityName() {
        return City.findCity(this.city).getCityName();
    }

    /**
     * To search terminals by parameters
     *
     * @param terminalId  The terminal id
     * @param status      The terminal status
     * @param firstResult Start index of the records
     * @param maxResults  Maximum records to be fetched
     * @param order
     * @return List of terminal
     */
    public static TypedQuery<Terminal> findTerminalsByParam(String terminalId, String status, Long terminalType, Long acquirer, int firstResult, int maxResults, String order) {
        EntityManager em = Terminal.entityManager();
        TypedQuery<Terminal> q = null;
        String query = "SELECT Terminal FROM Terminal AS terminal WHERE LOWER(terminal.status) != LOWER('" + Constants.TERMINAL_STATUS_DELETED + "')";
        if (status != null && !status.equals("") && !status.equals("-1")) {
            query = new StringBuilder(query).append(" AND LOWER(terminal.status) = LOWER(:status)").toString();
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
        if (order != null && !order.equals("")) {
            query = new StringBuilder(query).append(" ORDER BY ").append(order).toString();
        }
        q = (firstResult > -1 && maxResults > 0) ? em.createQuery(query, Terminal.class).setFirstResult(firstResult).setMaxResults(maxResults) : em.createQuery(query, Terminal.class);
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

    public static List findTerminalByAcquirerId(Long acquirerId) {
        TypedQuery<List> q = entityManager().createQuery("select Terminal from Terminal terminal where terminal.acquirer.id=:acquirerId", List.class);
        q.setParameter("acquirerId", acquirerId);
        return q.getResultList();
    }

    /**
     * To delete terminal by updated status to TERMINAL_STATUS_DELETED value
     *
     * @param terminal The terminal object
     * @return none
     */
    public static void deleteTerminalForAcquirer(Terminal terminal) {
        terminal.setStatus(Constants.TERMINAL_STATUS_DELETED);
        terminal.setModifiedBy("System");
        terminal.setModifiedTime(new Date());
        terminal.merge();
    }

    /**
     * Get total non deleted terminals count.
     *
     * @return Long total count
     */
    public static long totalTerminals() {
        return entityManager().createQuery("select count(o) from Terminal o WHERE LOWER(o.status) != LOWER('" + Constants.TERMINAL_STATUS_DELETED + "')", Long.class).getSingleResult();
    }

    /**
     * Get total non deleted terminals count based on parameters.
     *
     * @return Long total count
     */
    public static long totalTerminalsByParam(String terminalId, String status, Long terminalType, Long acquirer) {
        EntityManager em = Terminal.entityManager();
        TypedQuery<Long> q = null;
        String query = "SELECT count(terminal) FROM Terminal AS terminal WHERE LOWER(terminal.status) != LOWER('" + Constants.TERMINAL_STATUS_DELETED + "')";
        if (status != null && !status.equals("") && !status.equals("-1")) {
            query = new StringBuilder(query).append(" AND LOWER(terminal.status) = LOWER(:status)").toString();
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

        q = em.createQuery(query, Long.class);
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
        return q.getSingleResult();
    }
}

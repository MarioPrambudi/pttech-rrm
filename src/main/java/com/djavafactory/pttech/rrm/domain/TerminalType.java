package com.djavafactory.pttech.rrm.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;

@RooJavaBean
@RooToString
@RooEntity
public class TerminalType {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @Value("false")
    private Boolean deleted;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "terminalType")
    private List<Terminal> terminal;

    /**
     * To search terminal types by parameters
     *
     * @param searchText  The search text
     * @param deleted
     * @param order
     * @param firstResult Start index of the records
     * @param maxResults  Maximum records to be fetched   @return List of terminal type
     */
    public static TypedQuery<TerminalType> findTerminalTypesByParam(String searchText, Boolean deleted, String order, int firstResult, int maxResults) {
        EntityManager em = TerminalType.entityManager();
        TypedQuery<TerminalType> q = null;
        String query = "SELECT TerminalType FROM TerminalType AS terminalType";
        query = (deleted != null && !deleted.equals("") && deleted == true) ? new StringBuilder(query).append(" WHERE (terminalType.deleted = false or terminalType.deleted = :notDeleted)").toString() : new StringBuilder(query).append(" WHERE terminalType.deleted = false").toString();
        if (searchText != null && !searchText.equals("")) {
            query = new StringBuilder(query).append(" AND (LOWER(terminalType.name) LIKE LOWER(:searchText)")
                    .append(" OR LOWER(terminalType.description) LIKE LOWER(:searchText))").toString();
        }
        if (order != null && !order.equals("")) {
            query = new StringBuilder(query).append(" ORDER BY ").append(order).toString();
        }
        q = (firstResult > -1 && maxResults > 0) ? em.createQuery(query, TerminalType.class).setFirstResult(firstResult).setMaxResults(maxResults) : em.createQuery(query, TerminalType.class);
        if (deleted != null && !deleted.equals("") && deleted == true) {
            q.setParameter("notDeleted", deleted);
        }
        if (searchText != null && !searchText.equals("")) {
            q.setParameter("searchText", "%" + searchText + "%");
        }
        return q;
    }

    /**
     * Get total non deleted terminal types count.
     *
     * @return Long total count
     */
    public static long totalTerminalTypes() {
        return entityManager().createQuery("select count(o) from TerminalType o where o.deleted = false", Long.class).getSingleResult();
    }

    public static Long totalTerminalTypesByParam(String searchText, Boolean deleted) {
        EntityManager em = TerminalType.entityManager();
        TypedQuery<Long> q = null;
        String query = "SELECT count(terminalType) FROM TerminalType AS terminalType";
        query = (deleted != null && !deleted.equals("") && deleted == true) ? new StringBuilder(query).append(" WHERE (terminalType.deleted = false or terminalType.deleted = :notDeleted)").toString() : new StringBuilder(query).append(" WHERE terminalType.deleted = false").toString();
        if (searchText != null && !searchText.equals("")) {
            query = new StringBuilder(query).append(" AND (LOWER(terminalType.name) LIKE LOWER(:searchText)")
                    .append(" OR LOWER(terminalType.description) LIKE LOWER(:searchText))").toString();
        }
        q = em.createQuery(query, Long.class);
        if (deleted != null && !deleted.equals("") && deleted == true) {
            q.setParameter("notDeleted", deleted);
        }
        if (searchText != null && !searchText.equals("")) {
            q.setParameter("searchText", "%" + searchText + "%");
        }
        return q.getSingleResult();
    }
}

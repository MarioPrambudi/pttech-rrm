package com.djavafactory.pttech.rrm.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

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

    @ManyToMany
    private List<Terminal> terminal;

    /**
    * To search terminal types by parameters
    * @param searchText The search text
    * @param firstResult Start index of the records
    * @param maxResults  Maximum records to be fetched
    * @exception none
    * @return List of terminal type
    */
    public static TypedQuery<TerminalType> findTerminalTypesByParam(String searchText, int firstResult, int maxResults) {
        EntityManager em = new TerminalType().entityManager();
        TypedQuery<TerminalType> q = null;
        String query = "SELECT TerminalType FROM TerminalType AS terminalType WHERE terminalType.deleted = false";
        if (searchText != null && !searchText.equals("")) {
            query = new StringBuilder(query).append(" AND (LOWER(terminalType.name) LIKE LOWER(:searchText)")
                    .append(" OR LOWER(terminalType.description) LIKE LOWER(:searchText))").toString();
        }
        q = (firstResult > 0 && maxResults > 0) ? em.createQuery(query, TerminalType.class).setFirstResult(firstResult).setMaxResults(maxResults) : em.createQuery(query, TerminalType.class);
        if (searchText != null && !searchText.equals("")) {
            q.setParameter("searchText", "%" + searchText + "%");
        }
        return q;
    }
}

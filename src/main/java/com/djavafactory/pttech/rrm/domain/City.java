package com.djavafactory.pttech.rrm.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

@RooJavaBean
@RooToString
@RooEntity
@RooJson
public class City {

    @NotNull
    private String cityName;

    @NotNull
    @ManyToOne
    private Province acquirerState;

    /**
    * To get the list of cities by state
    * @param stateId The state id
    * @return List of cities by state
    */
    public static String findCitiesByState(long stateId) {
        TypedQuery<City> q = entityManager().createQuery("select City from City city where city.acquirerState.id = :stateId order by city.cityName", City.class);
        q.setParameter("stateId", stateId);
        return toJsonArray(q.getResultList());
    }
}

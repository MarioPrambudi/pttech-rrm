// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.djavafactory.pttech.rrm.domain;

import com.djavafactory.pttech.rrm.domain.Firmware;
import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Firmware_Roo_Entity {
    
    declare @type: Firmware: @Entity;
    
    @PersistenceContext
    transient EntityManager Firmware.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Firmware.id;
    
    @Version
    @Column(name = "version")
    private Integer Firmware.version;
    
    public Long Firmware.getId() {
        return this.id;
    }
    
    public void Firmware.setId(Long id) {
        this.id = id;
    }
    
    public Integer Firmware.getVersion() {
        return this.version;
    }
    
    public void Firmware.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Firmware.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Firmware.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Firmware attached = Firmware.findFirmware(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Firmware.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Firmware.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Firmware Firmware.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Firmware merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager Firmware.entityManager() {
        EntityManager em = new Firmware().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Firmware.countFirmwares() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Firmware o", Long.class).getSingleResult();
    }
    
    public static List<Firmware> Firmware.findAllFirmwares() {
        return entityManager().createQuery("SELECT o FROM Firmware o", Firmware.class).getResultList();
    }
    
    public static Firmware Firmware.findFirmware(Long id) {
        if (id == null) return null;
        return entityManager().find(Firmware.class, id);
    }
    
    public static List<Firmware> Firmware.findFirmwareEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Firmware o", Firmware.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}

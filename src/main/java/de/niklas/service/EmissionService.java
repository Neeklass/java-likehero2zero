package de.niklas.service;

import de.niklas.model.EmissionData;
import de.niklas.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EmissionService {
    
    @PersistenceContext(unitName = "HeroToZeroPU")
    private EntityManager em;

    /**
     * Speichert neue Emissionsdaten mit Status "pending".
     * @param data Die zu speichernden Emissionsdaten
     * @param author Der Autor/User, der die Daten erstellt
     */
    @Transactional
    public void saveNewData(EmissionData data, User author) {
        // Status auf 0 (pending) setzen
        data.setStatus(0);
        
        // Autor als lastModifiedBy setzen
        data.setLastModifiedBy(author);
        
        // Daten in der Datenbank speichern
        em.persist(data);
    }
}

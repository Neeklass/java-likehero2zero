package de.niklas.service;

import de.niklas.model.EmissionData;
import de.niklas.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

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
        
        // Land vom Autor übernehmen
        data.setCountry(author.getCountry());
        
        // Autor als lastModifiedBy setzen
        data.setLastModifiedBy(author);
        
        // Daten in der Datenbank speichern
        em.persist(data);
    }

    /**
     * Lädt alle Emissionsdaten mit Status "pending" (0).
     * Für Story 3: Editor sieht alle noch nicht freigegebenen Daten.
     * @return Liste aller pending Emissionsdaten
     */
    public List<EmissionData> getPendingData() {
        return em.createQuery(
            "SELECT e FROM EmissionData e WHERE e.status = 0 ORDER BY e.year DESC", 
            EmissionData.class)
            .getResultList();
    }

    /**
     * Genehmigt Emissionsdaten durch Setzen des Status auf 1 (approved).
     * Für Story 3: Editor kann Daten freigeben.
     * @param id Die ID des Datensatzes
     */
    @Transactional
    public void approveData(Integer id) {
        EmissionData data = em.find(EmissionData.class, id);
        if (data != null) {
            data.setStatus(1);
            em.merge(data);
        }
    }
}

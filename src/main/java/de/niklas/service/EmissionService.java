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

    @Transactional
    public void saveNewData(EmissionData data, User author) {
        data.setStatus(0);
        
        data.setCountry(author.getCountry());
        
        data.setLastModifiedBy(author);
        
        em.persist(data);
    }

    public List<EmissionData> getPendingData() {
        return em.createQuery(
            "SELECT e FROM EmissionData e WHERE e.status = 0 ORDER BY e.year DESC", 
            EmissionData.class)
            .getResultList();
    }

    @Transactional
    public void approveData(Integer id) {
        EmissionData data = em.find(EmissionData.class, id);
        if (data != null) {
            data.setStatus(1);
            em.merge(data);
        }
    }
}

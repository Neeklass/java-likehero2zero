package de.niklas.bean;

import de.niklas.model.Country;
import de.niklas.model.EmissionData;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Named 
@SessionScoped 
public class PublicDataBean implements Serializable {

    @PersistenceContext(unitName = "HeroToZeroPU")
    private EntityManager em;

    private List<Country> allCountries;
    private Integer selectedCountryId;
    private EmissionData latestData;

    @PostConstruct
    public void init() {
        allCountries = em.createQuery("SELECT c FROM Country c", Country.class).getResultList();
    }

    public void updateSelectedData() {
        if (selectedCountryId != null) {
            List<EmissionData> results = em.createQuery(
                "SELECT e FROM EmissionData e WHERE e.country.countryId = :cid AND e.status = 1 ORDER BY e.year DESC", 
                EmissionData.class)
                .setParameter("cid", selectedCountryId)
                .setMaxResults(1)
                .getResultList();

            latestData = results.isEmpty() ? null : results.get(0);
        }
    }

    public List<Country> getAllCountries() { return allCountries; }
    public Integer getSelectedCountryId() { return selectedCountryId; }
    public void setSelectedCountryId(Integer selectedCountryId) { this.selectedCountryId = selectedCountryId; }
    public EmissionData getLatestData() { return latestData; }
}
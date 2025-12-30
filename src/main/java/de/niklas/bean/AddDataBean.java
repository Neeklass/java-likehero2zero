package de.niklas.bean;

import de.niklas.model.Country;
import de.niklas.model.EmissionData;
import de.niklas.service.EmissionService;
import de.niklas.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class AddDataBean {
    
    @Inject
    private EmissionService emissionService;
    
    @Inject
    private LoginSessionBean loginSessionBean;
    
    @Inject
    private UserService userService;
    
    private EmissionData newData = new EmissionData();
    private Integer selectedCountryId;
    
    @PostConstruct
    public void init() {
        if (loginSessionBean.getUser() != null && loginSessionBean.getUser().getCountry() != null) {
            selectedCountryId = loginSessionBean.getUser().getCountry().getCountryId();
        }
    }
    
    public void save() {
        try {
            if (loginSessionBean.getUser() == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Fehler",
                        "Sie müssen angemeldet sein, um Daten hinzuzufügen."));
                return;
            }
            
            if (selectedCountryId == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Fehler",
                        "Bitte wählen Sie ein Land aus."));
                return;
            }
            
            Country country = userService.getAllCountries().stream()
                .filter(c -> c.getCountryId().equals(selectedCountryId))
                .findFirst()
                .orElse(null);
                
            if (country == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Fehler",
                        "Das ausgewählte Land konnte nicht gefunden werden."));
                return;
            }
            
            newData.setCountry(country);
            
            emissionService.saveNewData(newData, loginSessionBean.getUser());
            
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Erfolg",
                    "Emissionsdaten wurden erfolgreich zur Prüfung eingereicht."));
            
            newData = new EmissionData();
            if (loginSessionBean.getUser() != null && loginSessionBean.getUser().getCountry() != null) {
                selectedCountryId = loginSessionBean.getUser().getCountry().getCountryId();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Fehler",
                    "Speichern fehlgeschlagen: " + (e.getMessage() != null ? e.getMessage() : e.getClass().getName())));
        }
    }
    
    public List<Country> getAllCountries() {
        return userService.getAllCountries();
    }
    
    public EmissionData getNewData() {
        return newData;
    }
    
    public void setNewData(EmissionData newData) {
        this.newData = newData;
    }
    
    public Integer getSelectedCountryId() {
        return selectedCountryId;
    }
    
    public void setSelectedCountryId(Integer selectedCountryId) {
        this.selectedCountryId = selectedCountryId;
    }
}

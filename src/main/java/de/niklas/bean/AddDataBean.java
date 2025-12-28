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
    
    /**
     * Initialisiert das EmissionData-Objekt mit dem Land des eingeloggten Users.
     */
    @PostConstruct
    public void init() {
        if (loginSessionBean.getUser() != null && loginSessionBean.getUser().getCountry() != null) {
            selectedCountryId = loginSessionBean.getUser().getCountry().getCountryId();
        }
    }
    
    /**
     * Speichert neue Emissionsdaten über den EmissionService.
     */
    public void save() {
        try {
            // Prüfe zuerst, ob ein User eingeloggt ist
            if (loginSessionBean.getUser() == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Fehler",
                        "Sie müssen angemeldet sein, um Daten hinzuzufügen."));
                return;
            }
            
            // Setze das Land basierend auf der ausgewählten ID
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
            
            // Speichere Emissionsdaten mit dem eingeloggten User
            emissionService.saveNewData(newData, loginSessionBean.getUser());
            
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Erfolg",
                    "Emissionsdaten wurden erfolgreich zur Prüfung eingereicht."));
            
            // Neues Objekt für nächsten Eintrag mit Country vorbelegen
            newData = new EmissionData();
            if (loginSessionBean.getUser() != null && loginSessionBean.getUser().getCountry() != null) {
                selectedCountryId = loginSessionBean.getUser().getCountry().getCountryId();
            }
            
        } catch (Exception e) {
            e.printStackTrace(); // Gibt den vollen Stack Trace in die Konsole aus
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Fehler",
                    "Speichern fehlgeschlagen: " + (e.getMessage() != null ? e.getMessage() : e.getClass().getName())));
        }
    }
    
    /**
     * Lädt alle Länder für das Dropdown-Menü.
     * @return Liste aller Länder
     */
    public List<Country> getAllCountries() {
        return userService.getAllCountries();
    }
    
    // Getter & Setter
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

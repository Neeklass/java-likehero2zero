package de.niklas.bean;

import de.niklas.model.EmissionData;
import de.niklas.service.EmissionService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class AddDataBean {
    
    @Inject
    private EmissionService emissionService;
    
    @Inject
    private LoginSessionBean loginSessionBean;
    
    private EmissionData newData = new EmissionData();
    
    /**
     * Initialisiert das EmissionData-Objekt mit dem Land des eingeloggten Users.
     */
    @PostConstruct
    public void init() {
        if (loginSessionBean.getUser() != null) {
            newData.setCountry(loginSessionBean.getUser().getCountry());
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
            
            // Speichere Emissionsdaten mit dem eingeloggten User
            emissionService.saveNewData(newData, loginSessionBean.getUser());
            
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Erfolg",
                    "Emissionsdaten wurden erfolgreich zur Prüfung eingereicht."));
            
            // Neues Objekt für nächsten Eintrag
            newData = new EmissionData();
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Fehler",
                    "Speichern fehlgeschlagen: " + e.getMessage()));
        }
    }
    
    // Getter & Setter
    public EmissionData getNewData() {
        return newData;
    }
    
    public void setNewData(EmissionData newData) {
        this.newData = newData;
    }
}

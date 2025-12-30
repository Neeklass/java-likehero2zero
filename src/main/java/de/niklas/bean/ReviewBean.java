package de.niklas.bean;

import de.niklas.model.EmissionData;
import de.niklas.service.EmissionService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ReviewBean {
    
    @Inject
    private EmissionService emissionService;
    
    private List<EmissionData> pendingData;
    
    @PostConstruct
    public void init() {
        loadPendingData();
    }
    
    public void loadPendingData() {
        pendingData = emissionService.getPendingData();
    }
    
    public void approve(Integer id) {
        try {
            emissionService.approveData(id);
            
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Erfolg",
                    "Datensatz wurde erfolgreich freigegeben."));
            
            loadPendingData();
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Fehler",
                    "Freigabe fehlgeschlagen: " + e.getMessage()));
        }
    }
    
    public List<EmissionData> getPendingData() {
        return pendingData;
    }
    
    public void setPendingData(List<EmissionData> pendingData) {
        this.pendingData = pendingData;
    }
}

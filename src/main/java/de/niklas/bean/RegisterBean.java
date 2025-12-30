package de.niklas.bean;

import de.niklas.model.Country;
import de.niklas.model.User;
import de.niklas.service.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class RegisterBean {

    @Inject
    private UserService userService;

    private User user = new User();
    private Integer selectedCountryId;

    public void register() {
        try {
            if (selectedCountryId != null) {
                Country country = userService.getAllCountries().stream()
                    .filter(c -> c.getCountryId().equals(selectedCountryId))
                    .findFirst()
                    .orElse(null);
                user.setCountry(country);
            }
            
            userService.registerUser(user);
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Erfolg", 
                    "Benutzer '" + user.getUsername() + "' wurde erfolgreich registriert!"));
            
            user = new User();
            
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Fehler", 
                    "Registrierung fehlgeschlagen: " + e.getMessage()));
        }
    }

    public List<Country> getAllCountries() {
        return userService.getAllCountries();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Integer getSelectedCountryId() {
        return selectedCountryId;
    }
    
    public void setSelectedCountryId(Integer selectedCountryId) {
        this.selectedCountryId = selectedCountryId;
    }
}

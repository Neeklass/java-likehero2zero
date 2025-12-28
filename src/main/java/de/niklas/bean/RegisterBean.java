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

    /**
     * Registriert einen neuen User und zeigt eine Erfolgsmeldung an.
     */
    public void register() {
        try {
            userService.registerUser(user);
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Erfolg", 
                    "Benutzer '" + user.getUsername() + "' wurde erfolgreich registriert!"));
            
            // Neues User-Objekt für nächste Registrierung
            user = new User();
            
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Fehler", 
                    "Registrierung fehlgeschlagen: " + e.getMessage()));
        }
    }

    /**
     * Lädt alle Länder aus der Datenbank für das Dropdown-Menü.
     * @return Liste aller Länder
     */
    public List<Country> getAllCountries() {
        // Wir nutzen den Service, der bereits eine funktionierende Datenbankverbindung hat
        return userService.getAllCountries();
    }

    // Getter & Setter
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package de.niklas.bean;

import de.niklas.model.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginSessionBean implements Serializable {
    
    private User user;

    /**
     * Prüft, ob ein User eingeloggt ist.
     * @return true wenn ein User in der Session gespeichert ist
     */
    public boolean isLoggedIn() {
        return user != null;
    }

    /**
     * Prüft, ob der eingeloggte User ein Wissenschaftler ist.
     * @return true wenn der User die Rolle "SCIENTIST" hat
     */
    public boolean isScientist() {
        return user != null && "SCIENTIST".equals(user.getRole());
    }

    /**
     * Prüft, ob der eingeloggte User ein Editor ist.
     * @return true wenn der User die Rolle "EDITOR" hat
     */
    public boolean isEditor() {
        return user != null && "EDITOR".equals(user.getRole());
    }

    // Getter & Setter
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

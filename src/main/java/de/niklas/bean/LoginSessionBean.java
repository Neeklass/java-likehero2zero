package de.niklas.bean;

import de.niklas.model.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginSessionBean implements Serializable {
    
    private User user;

    public boolean isLoggedIn() {
        return user != null;
    }

    public boolean isScientist() {
        return user != null && "SCIENTIST".equals(user.getRole());
    }

    public boolean isEditor() {
        return user != null && "EDITOR".equals(user.getRole());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

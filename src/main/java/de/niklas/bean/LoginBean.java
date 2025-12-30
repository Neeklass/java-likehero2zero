package de.niklas.bean;

import de.niklas.model.User;
import de.niklas.service.UserService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private UserService userService;
    
    @Inject
    private LoginSessionBean loginSessionBean;

    private String username;
    private String password;
    private User currentUser;

    public String login() {
        User user = userService.findUserByUsername(username);
        
        if (user != null && userService.verifyPassword(password, user.getPasswordHash())) {
            currentUser = user;
            
            loginSessionBean.setUser(user);
            
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Login erfolgreich",
                    "Willkommen, " + user.getFirstname() + "!"));
            
            password = null;
            
            return "index?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Login fehlgeschlagen",
                    "Benutzername oder Passwort ist falsch."));
            return null;
        }
    }

    public String logout() {
        currentUser = null;
        username = null;
        password = null;
        
        loginSessionBean.setUser(null);
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        
        context.addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Logout erfolgreich",
                "Sie wurden erfolgreich abgemeldet."));
        
        return "index?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}

package de.niklas.service;

import de.niklas.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

@ApplicationScoped
public class UserService {
    
    @PersistenceContext(unitName = "HeroToZeroPU")
    private EntityManager em;

    /**
     * Registriert einen neuen User. Das Passwort wird automatisch gehasht.
     * @param user Der zu registrierende User (mit Klartext-Passwort im passwordHash-Feld)
     */
    @Transactional
    public void registerUser(User user) {
        // Hash das Passwort vor dem Speichern
        String hashedPassword = hashPassword(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);
        
        em.persist(user);
    }

    /**
     * Sucht einen User anhand des Usernamens.
     * @param username Der Username
     * @return Der gefundene User oder null
     */
    public User findUserByUsername(String username) {
        List<User> results = em.createQuery(
            "SELECT u FROM User u WHERE u.username = :username", User.class)
            .setParameter("username", username)
            .getResultList();
        
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Hasht ein Passwort mit BCrypt.
     * @param plainPassword Das Klartext-Passwort
     * @return Das gehashte Passwort
     */
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Überprüft, ob ein Passwort mit dem gespeicherten Hash übereinstimmt.
     * @param plainPassword Das Klartext-Passwort
     * @param hashedPassword Der gespeicherte Hash
     * @return true wenn das Passwort korrekt ist
     */
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    /**
     * Lädt alle Länder aus der Datenbank.
     * @return Liste aller Länder, sortiert nach Name
     */
    public List<de.niklas.model.Country> getAllCountries() {
        return em.createQuery("SELECT c FROM Country c ORDER BY c.name", de.niklas.model.Country.class)
                 .getResultList();
    }
}

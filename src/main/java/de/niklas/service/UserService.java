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

    @Transactional
    public void registerUser(User user) {
        String hashedPassword = hashPassword(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);
        
        em.persist(user);
    }

    public User findUserByUsername(String username) {
        List<User> results = em.createQuery(
            "SELECT u FROM User u LEFT JOIN FETCH u.country WHERE u.username = :username", User.class)
            .setParameter("username", username)
            .getResultList();
        
        return results.isEmpty() ? null : results.get(0);
    }

    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public List<de.niklas.model.Country> getAllCountries() {
        return em.createQuery("SELECT c FROM Country c ORDER BY c.name", de.niklas.model.Country.class)
                 .getResultList();
    }
}

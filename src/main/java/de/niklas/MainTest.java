package de.niklas;

import de.niklas.model.Country;
import de.niklas.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HeroToZeroPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Country germany = new Country();
            germany.setName("Deutschland");
            germany.setIsoCode("GER");
            em.persist(germany);


            User niklas = new User();
            niklas.setUsername("niklas_dev");
            niklas.setPasswordHash("geheim123"); 
            niklas.setFirstname("Niklas");
            niklas.setRole("EDITOR"); 
            niklas.setCountry(germany); 
            niklas.setCity("Schleswig");
            niklas.setZipCode("24837");
            
            em.persist(niklas);

            em.getTransaction().commit();
            System.out.println("--- Connection successful. ---");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
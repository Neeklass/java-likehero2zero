package de.niklas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainTest {
    public static void main(String[] args) {
        // "HeroToZeroPU" muss exakt der Name aus deiner persistence.xml sein!
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HeroToZeroPU");
        EntityManager em = emf.createEntityManager();

        System.out.println("--- Connection successful. SQL tables should exist now. ---");

        em.close();
        emf.close();
    }
}

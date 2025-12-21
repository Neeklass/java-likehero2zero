package de.niklas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    private String firstname;
    private String lastname;
    
    @Column(nullable = false)
    private String role; // "SCIENTIST" oder "EDITOR"

    private String street;
    @Column(name = "street_suffix")
    private String streetSuffix;
    private String city;
    @Column(name = "zip_code")
    private String zipCode;

    // Standard-Konstruktor für JPA
    public User() {}

    // Getter und Setter
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getStreetSuffix() { return streetSuffix; }
    public void setStreetSuffix(String streetSuffix) { this.streetSuffix = streetSuffix; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
}
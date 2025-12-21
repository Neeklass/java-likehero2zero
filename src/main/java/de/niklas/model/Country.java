package de.niklas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "countries") 
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Integer countryId;

    @Column(nullable = false)
    private String name;

    @Column(name = "iso_code", length = 3)
    private String isoCode;

    // Leerer Konstruktor (Wichtig für JPA!)
    public Country() {
    }

    // Konstruktor zum schnellen Erstellen von Objekten
    public Country(String name, String isoCode) {
        this.name = name;
        this.isoCode = isoCode;
    }

    // Getter und Setter
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
}
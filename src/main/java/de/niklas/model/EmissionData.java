package de.niklas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "emission_data")
public class EmissionData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dataset_id")
    private Integer datasetId;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(nullable = false)
    private Integer year;

    @Column(name = "co2_value", nullable = false, precision = 15, scale = 3)
    private BigDecimal co2Value;

    @Column(nullable = false)
    private Integer status = 0; // 0 = pending, 1 = approved

    @ManyToOne
    @JoinColumn(name = "last_modified_by")
    private User lastModifiedBy;

    public EmissionData() {}

    // Getter & Setter
    public Integer getDatasetId() { return datasetId; }
    public void setDatasetId(Integer datasetId) { this.datasetId = datasetId; }
    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public BigDecimal getCo2Value() { return co2Value; }
    public void setCo2Value(BigDecimal co2Value) { this.co2Value = co2Value; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public User getLastModifiedBy() { return lastModifiedBy; }
    public void setLastModifiedBy(User lastModifiedBy) { this.lastModifiedBy = lastModifiedBy; }
}
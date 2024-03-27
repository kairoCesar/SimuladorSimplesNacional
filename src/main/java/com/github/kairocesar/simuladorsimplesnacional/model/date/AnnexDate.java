package com.github.kairocesar.simuladorsimplesnacional.model.date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AnnexDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonBackReference
    private Long id;

    @JsonBackReference
    private Double rbt12;
    @JsonBackReference
    private Double revenueValue;
    private String date;

    public AnnexDate(Double rbt12, Double revenueValue, String date) {
        this.rbt12 = rbt12;
        this.revenueValue = revenueValue;
        this.date = date;
    }

    @Deprecated
    public AnnexDate() {
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Double getRbt12() {
        return rbt12;
    }

    public Double getRevenueValue() {
        return revenueValue;
    }
}

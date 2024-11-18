package com.OperationalRisk.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class KRI {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private double value;
        private Date dateRecorded;
        private String riskType;

        private double weight;

    public KRI(Long id, String name, double value, Date dateRecorded, String riskType, double weight) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.dateRecorded = dateRecorded;
        this.riskType = riskType;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public KRI() {
    }

    public KRI(Long id, String name, double value, Date dateRecorded, String riskType) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.dateRecorded = dateRecorded;
        this.riskType = riskType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }
}



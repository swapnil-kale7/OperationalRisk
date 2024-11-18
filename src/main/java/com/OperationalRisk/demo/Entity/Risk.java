package com.OperationalRisk.demo.Entity;

import jakarta.persistence.*;

@Entity
public class Risk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String severity;
    private String status;
    private String location;
    private String mitigation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private userr createdBY;

    public userr getCreatedBY() {
        return createdBY;
    }

    public void setCreatedBY(userr createdBY) {
        this.createdBY = createdBY;
    }

    public Risk(Long id, String name, String description, String severity, String status, String location, String mitigation, userr createdBY) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.severity = severity;
        this.status = status;
        this.location = location;
        this.mitigation = mitigation;
        this.createdBY = createdBY;
    }

    public Risk() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMitigation() {
        return mitigation;
    }

    public void setMitigation(String mitigation) {
        this.mitigation = mitigation;
    }
}


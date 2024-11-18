package com.OperationalRisk.demo.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class userr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int ID;

    private String username;
    private String password;
    private String role;

    public userr(int ID, String username, String password, String role) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public userr() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

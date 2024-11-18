package com.OperationalRisk.demo.config;

import com.OperationalRisk.demo.Entity.userr;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;


public class customUser implements UserDetails {

    userr userr;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         return Arrays.asList(new SimpleGrantedAuthority("ROLE_"+userr.getRole()));
    }

    public customUser(com.OperationalRisk.demo.Entity.userr userr) {
        this.userr = userr;
    }

    @Override
    public String getPassword() {
        return userr.getPassword();
    }

    @Override
    public String getUsername() {
        return userr.getUsername();
    }
}

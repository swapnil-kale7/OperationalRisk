package com.OperationalRisk.demo.config;

import com.OperationalRisk.demo.Entity.userr;
import com.OperationalRisk.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
public class customUserDetailService  implements UserDetailsService {

    @Autowired
    UserRepo userRepo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userr Userr =userRepo.findByUsername(username);

        if (Userr==null){
            throw  new UsernameNotFoundException("user "+ username +" not found");
        }
        return new customUser(Userr);


    }


//    ------old without userDetails
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        userr Userr =userRepo.findByUsername(username);
//
//        if (Userr==null){
//            throw  new UsernameNotFoundException("user "+ username +" not found");
//        }
//
//        else
//            return new User(Userr.getUsername(),Userr.getPassword(),getAuthorities(Userr)) {
//            };
//    }

//    private Collection<? extends GrantedAuthority> getAuthorities(userr user)
//    { return Arrays.asList(new SimpleGrantedAuthority("ROLE_"+user.getRole())); }



    //SimpleGrantedAuthority implements the GrantedAuthority interface. GrantedAuthority is a core interface in Spring Security, representing an authority granted to the authenticated user. SimpleGrantedAuthority is a concrete implementation of this interface, often used to represent roles or permissions.
//It takes a string representing the role or authority and converts it into a GrantedAuthority object that Spring Security can use for authorization decisions.
}

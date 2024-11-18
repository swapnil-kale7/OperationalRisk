package com.OperationalRisk.demo.Service;

import com.OperationalRisk.demo.Entity.userr;
import com.OperationalRisk.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

        @Autowired
        private UserRepo userRepository;
        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        public userr findByUsername(String username) {
            return userRepository.findByUsername(username);
        }

        public void save(userr user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }



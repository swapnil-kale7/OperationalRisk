package com.OperationalRisk.demo.controller;

import com.OperationalRisk.demo.Entity.userr;
import com.OperationalRisk.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Controller
public class Usercontroller {

    @Autowired
    UserService userService;

    @GetMapping("/Showlogin")
    public String Showlogin() {
        return "login.html";
    }



    @GetMapping("/register")
        public String showRegistrationForm(Model model) {
            model.addAttribute("user", new userr());
            return "Register";
        }
    @PostMapping("/register")
        public String registerUser(@ModelAttribute("user") userr user) {
            userService.save(user);
            return "redirect:/Showlogin";
        }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard"; // Ensure you have a dashboard.html file
    }


}

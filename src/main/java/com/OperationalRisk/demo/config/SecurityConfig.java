package com.OperationalRisk.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Autowired
    customUserDetailService customUserDetailService;



    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserDetailsService(customUserDetailService);
    return authenticationProvider;
}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf->csrf.disable()).cors(Customizer.withDefaults());
        httpSecurity.authorizeHttpRequests(configurer ->
                configurer.requestMatchers("/register", "/error","/risks/*")
                        .permitAll()
                        .anyRequest().authenticated());
        httpSecurity.formLogin(
                form -> form.loginPage("/Showlogin")
                        .loginProcessingUrl("/authenticateTheUser")
                        .defaultSuccessUrl("/risks",true)
                        .permitAll());
        return httpSecurity.build();
    }
}
        ;




//        httpSecurity.csrf(csrf -> csrf.disable());
//    http.authorizeHttpRequests(configurer ->
//                    configurer.anyRequest().authenticated())
//            .formLogin(form->
//                    form.loginPage("/showLogin")
//                            .loginProcessingUrl("/authenticateTheUser")
//                            .permitAll());
//return httpSecurity.build();
//        return httpSecurity.build();

//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//    httpSecurity.authorizeRequests()
//            .requestMatchers("/login","/register","/dashboard","/error").permitAll()
//            .requestMatchers("/admin/**").hasRole("ADMIN")
//            .anyRequest().authenticated()
//            .and()
//            .formLogin(form ->
//                    form.loginPage("/login")
//                            .defaultSuccessUrl("/dashboard",true).permitAll()
//                            )
//            .logout(logout ->
//                    logout.permitAll());
//    httpSecurity.csrf(csrf -> csrf.disable());
//
//    return httpSecurity.build();
//
//    http.authorizeHttpRequests(configurer ->
//                    configurer.anyRequest().authenticated())
//            .formLogin(form->
//                    form.loginPage("/showLogin")
//                            .loginProcessingUrl("/authenticateTheUser")
//                            .permitAll());
//return httpSecurity.build();
//}

//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            http
//                    .authorizeRequests()
//                    .antMatchers("/login", "/register").permitAll()
//                    .antMatchers("/admin/**").hasRole("ADMIN")
//                    .anyRequest().authenticated()
//                    .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .defaultSuccessUrl("/dashboard")
//                    .permitAll()
//                    .and()
//                    .logout()
//                    .permitAll();
//            return http.build();
//        }
//    }}




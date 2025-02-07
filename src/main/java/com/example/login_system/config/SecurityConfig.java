package com.example.login_system.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configure which requests are permitted without authentication
                .authorizeHttpRequests(authorize -> authorize
                        // Allow forwarded requests (useful for error pages and view resolution)
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        // Allow access to static resources and the login page itself
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/error/**", "/loginPage**").permitAll()
                        // Require authentication for any other request
                        .anyRequest().authenticated()
                )
                // Configure form-based login
                .formLogin(form -> form
                        // Set a custom login page; ensure a controller exists that maps to "/login"
                        .loginPage("/loginPage")
                        // After a successful login, always redirect to "/home"
                        .defaultSuccessUrl("/home", true)
                        // Permit all users to see the login page
                        .permitAll()
                )
                // Configure logout functionality
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                ).csrf(AbstractHttpConfigurer::disable);
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

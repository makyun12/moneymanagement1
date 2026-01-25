package com.project.moneymanagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/login").permitAll() // CSS dan Login boleh diakses siapa saja
                .anyRequest().authenticated() // Sisanya (termasuk simpan data) harus login
            )
            .formLogin(form -> form
            	    .loginPage("/login")
            	    .defaultSuccessUrl("/", true) // Berhasil login -> Masuk ke Menu
            	    .permitAll()
            	)
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}
package com.futshop.futshop.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    private FilterToken filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http.csrf().disable()
                .authorizeHttpRequests(
                    authorizeConfig -> {
                        authorizeConfig.requestMatchers("/pedidos/cliente/*").authenticated();
                        authorizeConfig.requestMatchers("/pedidos/pedido/*").authenticated();
                        authorizeConfig.requestMatchers(HttpMethod.DELETE, "/produtos/*").authenticated()
                                .and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
                        authorizeConfig.anyRequest().permitAll();
                    }
                ).formLogin(Customizer.withDefaults())
                 .build();
    }
}

package com.futshop.futshop.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
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
                            //Rotas liberadas para todos
                            authorizeConfig.requestMatchers(HttpMethod.POST, "/usuarios/email/**").permitAll();
                            authorizeConfig.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();
                            authorizeConfig.requestMatchers(HttpMethod.GET, "/produtos/**").permitAll();

                            //Rotas liberadas apenas para usuários logados
                            authorizeConfig.requestMatchers("/produtos/**").authenticated();
                            authorizeConfig.requestMatchers("/usuarios/**").authenticated();
                            authorizeConfig.requestMatchers("/carrinhos/**").authenticated();
                            authorizeConfig.requestMatchers("/pedidos/**").authenticated()
                            .and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

                            authorizeConfig.anyRequest().permitAll();
                        }
                ).build();
    }
}

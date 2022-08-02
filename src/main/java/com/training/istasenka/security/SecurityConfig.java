package com.training.istasenka.security;


import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import static com.training.istasenka.util.UserRole.*;
import static org.springframework.http.HttpMethod.*;

@EnableGlobalMethodSecurity(jsr250Enabled = true)
@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        var keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests()
                .antMatchers(POST, "/tickets").hasAnyRole(EMPLOYEE.name(), MANAGER.name())
                .antMatchers(PUT, "/tickets/{id}").hasAnyRole(EMPLOYEE.name(), MANAGER.name())
                .antMatchers(PATCH, "/tickets/{id}").hasAnyRole(EMPLOYEE.name(), MANAGER.name())
                .antMatchers(DELETE, "/tickets/{id}").hasAnyRole(EMPLOYEE.name(), MANAGER.name())
                .antMatchers(PUT, "/users/{id}").hasAnyRole(EMPLOYEE.name())
                .antMatchers(DELETE, "/users/{name}").hasAnyRole(EMPLOYEE.name())
                .antMatchers(GET, "/users/engineers/{name}/rating").hasAnyRole(EMPLOYEE.name(), MANAGER.name())
                .antMatchers(GET, "/tickets/{ticket_id}/feedbacks/").hasRole(ENGINEER.name())
                .antMatchers(GET, "/users/login").permitAll()
                .antMatchers(POST, "/users").permitAll()
                .anyRequest().authenticated();
        http.csrf().disable();
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }


}

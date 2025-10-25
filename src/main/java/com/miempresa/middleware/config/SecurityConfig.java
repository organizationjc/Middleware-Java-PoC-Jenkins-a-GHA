package com.miempresa.middleware.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF porque usaremos un middleware stateless (REST)
            .csrf(csrf -> csrf.disable())
            
            // Configurar la autorización de las peticiones
            .authorizeHttpRequests(authz -> authz
                // Los endpoints de "admin" requieren el ROL_ADMIN
                .requestMatchers(HttpMethod.POST, "/api/v1/productos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/productos/**").hasRole("ADMIN")
                
                // Los endpoints de lectura (GET) solo requieren estar autenticados
                .requestMatchers("/api/v1/productos/**").hasAnyRole("USER", "ADMIN")
                
                // Cualquier otra petición debe estar autenticada
                .anyRequest().authenticated()
            )
            
            // Usar autenticación básica HTTP
            .httpBasic(Customizer.withDefaults())
            
            // Configurar la gestión de sesión como STATELESS
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Creación de usuarios en memoria para el ejemplo
        
        UserDetails user = User.builder()
            .username("user")
            .password("{noop}password123") // {noop} indica que no se usa encriptación (solo para demos)
            .roles("USER")
            .build();

        UserDetails admin = User.builder()
            .username("admin")
            .password("{noop}adminpass")
            .roles("USER", "ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
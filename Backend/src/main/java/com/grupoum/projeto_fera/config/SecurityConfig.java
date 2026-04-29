package com.grupoum.projeto_fera.config;

import com.grupoum.projeto_fera.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Desabilita CSRF para API REST
            .csrf(AbstractHttpConfigurer::disable)
            // Permite frames para o H2 Console
            .headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            .authorizeHttpRequests(auth -> auth
                // H2 Console - público
                .requestMatchers("/h2-console/**").permitAll()

                // Produtos: leitura para USER e ADMIN, escrita só ADMIN
                .requestMatchers(HttpMethod.GET, "/api/produtos/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/produtos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/produtos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/produtos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/produtos/**").hasRole("ADMIN")

                // Usuários: apenas ADMIN
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                // Qualquer outra rota precisa de autenticação
                .anyRequest().authenticated()
            )
            // Autenticação HTTP Basic (padrão Spring Security)
            .httpBasic(Customizer.withDefaults())
            .authenticationProvider(authenticationProvider());

        return http.build();
    }
}

package com.obss.pokedex.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {
    private static final String[] AUTH_WHITELIST = {
            "/h2-console",
            "/h2-console/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/webjars/**",
            "/graphiql",
            "/api/graphiql",
    };
    private final PasswordEncoder passwordEncoder;
    private final UserAuthService userAuthService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userAuthService);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(customizer -> customizer.requestMatchers(AUTH_WHITELIST).anonymous()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .anyRequest().hasAnyRole("ADMIN", "USER"))
                .exceptionHandling(customizer -> customizer.accessDeniedHandler((req, resp, ex) -> resp.setStatus(HttpServletResponse.SC_FORBIDDEN))
                        .authenticationEntryPoint((req, resp, ex) -> resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED)))
                .formLogin(customizer -> customizer
                        .loginProcessingUrl("/login")
                        .successHandler(/*(req, resp, auth) -> resp.setStatus(HttpServletResponse.SC_OK),*/new UserCustomAuthenticationSuccessHandler())
                        .failureHandler((req, resp, ex) -> resp.setStatus(HttpServletResponse.SC_FORBIDDEN)))
                .sessionManagement(customizer -> customizer.invalidSessionStrategy((req, resp) -> resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED)))
                .logout(customizer -> customizer.logoutUrl("/logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()))
                .csrf(customizer -> customizer.disable())
                .cors(customizer -> customizer
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                            config.setAllowedMethods(Collections.singletonList("*"));
                            config.setAllowedHeaders(Collections.singletonList("*"));
                            config.setAllowCredentials(true);
                            config.setMaxAge(3600L);
                            return config;
                        }));

        return http.build();

    }
}

package com.caglacakir.config;

import com.caglacakir.jwt.AuthEntryPoint;
import com.caglacakir.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String AUTHENTICATE = "/authenticate"; //bu iki satır için iki servis yazılacak :D
    public static final String REGISTER = "/register";
    public static final String REFRESH_TOKEN = "/refreshToken";

    public static final String[] SWAGGER_PATHS = {
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**"
    };

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    //tüm konfigurasyonlar burada yapılır
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)  throws Exception{
        http.csrf(csrf -> csrf.disable())//csrf hatalar fırlatır disable hale getirildi

                .authorizeHttpRequests(request->
                        request.requestMatchers(AUTHENTICATE, REGISTER , REFRESH_TOKEN).permitAll()
                                .requestMatchers(SWAGGER_PATHS).permitAll()

                .anyRequest().authenticated())
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(authEntryPoint))

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();

    }

}

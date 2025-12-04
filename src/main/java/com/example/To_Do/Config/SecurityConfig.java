package com.example.To_Do.Config;

import com.example.To_Do.service.CustomeUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomeUserDetailService userDetailsService;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           DaoAuthenticationProvider provider) throws Exception {

        http.authenticationProvider(provider);

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register","/","/saveUser","/verify-otp","/resend-otp","/login","/css/**","/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/todo", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/login?logout=true");
                        })
                        .invalidateHttpSession(true)
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionFixation(sessionFix -> sessionFix.migrateSession())
                        .invalidSessionUrl("/login?timeout=true")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/logout")
                );

        return http.build();
    }
}

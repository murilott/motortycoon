package br.edu.univille.motortycoon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
			.authorizeHttpRequests(auth -> auth
			.requestMatchers("/login/registrar", "/login", "/").permitAll()
			.anyRequest().authenticated()
		)
        .formLogin((formLogin) ->
        formLogin
            .usernameParameter("email")
            .passwordParameter("senha")
            .loginPage("/login")
            // .failureUrl("/usuario/login?failed")
            // .loginProcessingUrl("/login")
        )
			// .formLogin(Customizer.withDefaults())
			.logout(logout -> logout.logoutUrl("/login").logoutSuccessUrl("/login"));


        return http.build();
    }
}

package br.edu.univille.motortycoon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
			.authorizeHttpRequests(auth -> auth
			.requestMatchers("/registrar", "/login", "/").permitAll()
			.anyRequest().authenticated()
		)
        .formLogin((formLogin) ->
        formLogin
            .usernameParameter("email")
            .passwordParameter("senha")
            .loginPage("/usuario")
            // .failureUrl("/usuario/login?failed")
            .loginProcessingUrl("/usuario/login/process")
        )
			// .formLogin(Customizer.withDefaults())
			.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"));


        return http.build();
    }
}

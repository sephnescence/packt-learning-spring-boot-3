package io.sephnescence.github.learningspringboot3.ch2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    /*
    A note on Lambdas since this is my first time using them
    CommandLineRunner is a "single abstract method" (SAM), meaning that it has just one method
    This lets us instantiate CommandLineRunner using a lambda expression instead of creating
        an anonymous class
     */
    @Bean
    CommandLineRunner initUsers(UserManagementRepository userManagementRepository) {
        return args -> { // This is a Lambda function
            userManagementRepository.save(new UserAccount("user", "password", "ROLE_USER"));
            userManagementRepository.save(new UserAccount("admin", "password", "ROLE_ADMIN"));
        };
    }

    @Bean
    UserDetailsService userService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username).asUser();
    }

    @Bean
    SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
        System.out.println("Using configureSecurity to create SecurityFilterChain...");
        // This is deprecated though... :/
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/multi-field-search").authenticated()
                .requestMatchers(HttpMethod.GET, "/multi-field-search").authenticated()
                .requestMatchers(HttpMethod.GET, "/", "/react", "index.js").authenticated()
                .requestMatchers(HttpMethod.POST, "/new-video-entity").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/new-video-entity").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/**").authenticated() // Unsure how to authenticate though
                .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN") // Unsure how to authenticate though
                .anyRequest().denyAll()
                .and()
                .formLogin()
                .and()
                .httpBasic();

        return http.build();
    }
}

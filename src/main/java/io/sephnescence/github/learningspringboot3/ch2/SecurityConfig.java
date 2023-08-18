package io.sephnescence.github.learningspringboot3.ch2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // Might also see @EnableGlobalAuthentication in other codebases, which has been deprecated now. If I see it, the other security annotations will be different
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
            userManagementRepository.save(new UserAccount("alice", "password", "ROLE_USER"));
            userManagementRepository.save(new UserAccount("bob", "password", "ROLE_USER"));
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
                .requestMatchers(HttpMethod.POST, "/delete-video-entity/{videoId}").authenticated()
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

        // This is how you would disable CSRF. Technically you would need to do this to serve API requests
        //  but the book suggests you'd need to split this into two applications. Kinda annoying, so I'm
        //  leaving the API broken for now
        // http.authorizeHttpRequests().and().csrf().disable();

        return http.build();
    }
}

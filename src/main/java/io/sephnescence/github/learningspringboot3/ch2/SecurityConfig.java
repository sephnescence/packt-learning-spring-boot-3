package io.sephnescence.github.learningspringboot3.ch2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class SecurityConfig {
    // We were directed to add this, but then we immediately needed to delete it, and it never even told us...
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//
//        // withDefaultPasswordEncoder is deprecated now, but the book warns against using it anyways
//        //  see https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html#authentication-password-storage
//
//        userDetailsManager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build()
//        );
//        userDetailsManager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("password")
//                        .roles("ADMIN")
//                        .build()
//        );
//        return userDetailsManager;
//    }

    /*
    A note on Lambdas since this is my first time using them
    CommandLineRunner is a "single abstract method" (SAM), meaning that it has just one method
    This lets us instantiate CommandLineRunner using a lambda expression instead of creating
        an anonymous class
     */
    @Bean
    CommandLineRunner initUsers(UserManagementRepository userManagementRepository) {
        return args -> { // This is a Lambda function
            userManagementRepository.save(new UserAccount("user", "password", "ROLE_USER")); // It was "User" earlier hmm
            userManagementRepository.save(new UserAccount("admin", "password", "ROLE_ADMIN"));
        };
    }

    @Bean
    UserDetailsService userService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username).asUser();
    }
}

package io.sephnescence.github.learningspringboot3.ch2;

import org.springframework.data.repository.Repository;

// This is different to UserManagementRepository because it doesn't extend JpaRepository
//  This means that it will only have the repository methods defined here
public interface UserRepository extends Repository<UserAccount, Long> {
    // This is still a Custom Finder
    UserAccount findByUsername(String username);
}

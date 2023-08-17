package io.sephnescence.github.learningspringboot3.ch2;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserManagementRepository extends JpaRepository<UserAccount, Long> {

}

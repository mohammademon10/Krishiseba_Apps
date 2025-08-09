package com.krishiseba.krishisebaapp.repository;

import com.krishiseba.krishisebaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query to find a user by their email
    // Spring Data JPA automatically implements this method based on its name
    Optional<User> findByEmail(String email);

}
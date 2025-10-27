package com.repository;

import com.entity.User;
import com.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByUserName(String userName);

    Optional<User> findByUserName(String userName);

    List<User> findByActiveTrue();

    List<User> findByRole(UserRole role);

    List<User> findByActiveAndRole(boolean active, UserRole role);
}

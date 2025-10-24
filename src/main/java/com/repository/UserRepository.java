package com.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.dto.response.CardResponse;
import com.entity.User;
import java.util.List;

import com.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByUserName(String username);

    Optional<User> findByUserName(String username);

    List<User> findByActiveTrue();

    List<User> findByRole(UserRole role);

    List<User> findByActiveAndRole(boolean active, UserRole role);
}

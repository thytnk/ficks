package com.fujielectric.ficks.jpa;

import com.fujielectric.ficks.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM users u WHERE u.empNumber = :empNumber and u.disabled = FALSE")
    Optional<User> findEnabledUser(@Param("empNumber") String empNumber);
}

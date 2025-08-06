package com.example.DailyLedger.REST.API.repository;

import com.example.DailyLedger.REST.API.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT u.verified FROM User u WHERE u.email = :email")
    boolean isUserVerifiedByEmail(@Param("email") String email);


    Optional<User> findByEmail(String email);


}

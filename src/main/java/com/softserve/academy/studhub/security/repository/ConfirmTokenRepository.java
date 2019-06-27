package com.softserve.academy.studhub.security.repository;

import com.softserve.academy.studhub.security.model.ConfirmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmTokenRepository extends JpaRepository<ConfirmToken, Long> {

    ConfirmToken findByToken(String token);
}

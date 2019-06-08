package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

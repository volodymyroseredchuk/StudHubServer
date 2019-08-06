package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FreelancerRepository extends JpaRepository<Freelancer, Integer> {

    @Query("select avg(f.quality) from Freelancer f where f.user.username = :username")
    Double avgQualityByUserUsername(@Param("username") String username);

    @Query("select avg(f.price) from Freelancer f where f.user.username = :username")
    Double avgPriceByUserUsername(@Param("username") String username);

    @Query("select avg(f.velocity) from Freelancer f where f.user.username = :username")
    Double avgVelocityByUserUsername(@Param("username") String username);

    @Query("select avg(f.contact) from Freelancer f where f.user.username = :username")
    Double avgContactByUserUsername(@Param("username") String username);
}

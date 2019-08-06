package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("select avg(c.clarity) from Customer c where c.user.username = :username")
    Double avgClarityByUserUsername(@Param("username") String username);

    @Query("select avg(c.contact) from Customer c where c.user.username = :username")
    Double avgContactByUserUsername(@Param("username") String username);

    @Query("select avg(c.formulation) from Customer c where c.user.username = :username")
    Double avgFormulationByUserUsername(@Param("username") String username);

    @Query("select avg(c.payment) from Customer c where c.user.username = :username")
    Double avgPaymentByUserUsername(@Param("username") String username);
}

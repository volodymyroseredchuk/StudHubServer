package com.softserve.academy.studhub.repository;

import com.softserve.academy.studhub.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserCreatorUsername(String userCreatorId);

    List<Order> findByUserExecutorUsername(String userExecutorId);

    @Query("select count(o.id) from Order o where o.userExecutor.username = :username and o.task.status='DONE'")
    Integer countByFreelancerAndTaskDone(@Param("username") String username);
}

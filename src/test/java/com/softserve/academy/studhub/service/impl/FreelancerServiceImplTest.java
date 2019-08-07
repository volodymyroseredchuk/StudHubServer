package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Freelancer;
import com.softserve.academy.studhub.entity.Order;
import com.softserve.academy.studhub.repository.FreelancerRepository;
import com.softserve.academy.studhub.service.FreelancerService;
import com.softserve.academy.studhub.service.OrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FreelancerServiceImplTest {

    @Mock
    private FreelancerRepository freelancerRepository;

    @Mock
    private OrderService orderService;

    private FreelancerService freelancerService;

    @Before
    public void init() {
        freelancerService = new FreelancerServiceImpl(freelancerRepository, orderService);
    }

    @Test
    public void add() {

        Integer orderId = 1;
        Integer freelancerId = 1;

        Freelancer freelancer = new Freelancer();
        freelancer.setId(freelancerId);

        Order order = new Order();

        when(freelancerRepository.saveAndFlush(freelancer)).thenReturn(freelancer);
        when(orderService.findById(orderId)).thenReturn(order);

        Freelancer result = freelancerService.add(freelancer, orderId);

        Assert.assertEquals(result.getId(), freelancer.getId());
    }
}
package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.Customer;
import com.softserve.academy.studhub.entity.Freelancer;
import com.softserve.academy.studhub.entity.ResultSubmission;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {

    private Integer id;

    private UserDTO userCreator;

    private UserDTO userExecutor;

    private ProposalDTO proposal;

    private TaskDTO task;

    private ResultSubmission resultSubmission;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private FreelancerDTO freelancer;

    private CustomerDTO customer;

}

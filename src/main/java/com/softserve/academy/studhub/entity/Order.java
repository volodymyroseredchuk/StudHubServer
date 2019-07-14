package com.softserve.academy.studhub.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_creator_id", referencedColumnName = "id")
    private User userCreator;

    @ManyToOne
    @JoinColumn(name = "user_executor_id", referencedColumnName = "id")
    private User userExecutor;

    @ManyToOne
    @JoinColumn(name = "proposal_id", referencedColumnName = "id")
    private Proposal proposal;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "result_submission_id", referencedColumnName = "id")
    private ResultSubmission resultSubmission;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @Column(name="end_date")
    @EqualsAndHashCode.Exclude
    private LocalDateTime endDate;




}

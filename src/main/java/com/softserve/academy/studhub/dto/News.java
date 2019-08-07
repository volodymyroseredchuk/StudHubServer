package com.softserve.academy.studhub.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class News {


    private Integer id;

    private String title;

    @EqualsAndHashCode.Exclude
    private String body;

    private Date creationDate;

    private String sourceUrl;



}

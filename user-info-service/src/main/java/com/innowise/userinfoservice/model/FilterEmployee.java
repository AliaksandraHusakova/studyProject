package com.innowise.userinfoservice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class FilterEmployee {

    private String lastName;
    private String firstName;
    private String middleName;

    private LocalDate dateFrom;
    private LocalDate dateTo;

    private List<Integer> roleIds;
}

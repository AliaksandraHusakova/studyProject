package com.innowise.userinfoservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sorting {

    private int pageNumber;
    private String sortField;
    private String sortDir;
}

package com.epam.esm.model;

import lombok.Data;

//todo
@Data
public class Filter {
    private String tag = "";
    private String name = "";
    private String description = "";
    private String orderBy = "name";
    private String direction = "asc";
}

package com.epam.esm.model;

import lombok.Data;

@Data
public class Filters {
    private String tag = "";
    private String name = "";
    private String description = "";
    private String orderBy = "";
}

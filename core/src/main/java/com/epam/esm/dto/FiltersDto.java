package com.epam.esm.dto;

import lombok.Data;

@Data
public class FiltersDto {
    private String tag = "";
    private String name = "";
    private String description = "";
    private String orderBy = "name";
    private String direction = "asc";
}

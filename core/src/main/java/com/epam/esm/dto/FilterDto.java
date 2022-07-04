package com.epam.esm.dto;

import lombok.Data;

// TODO: 04.07.2022 javadoc? immutable
@Data
public class FilterDto {
    private String tag = "";
    private String name = "";
    private String description = "";
    private String orderBy = "name";
    private String direction = "asc";
}

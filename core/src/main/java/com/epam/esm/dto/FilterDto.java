package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FilterDto {
    private String tag = "";
    private String name = "";
    private String description = "";
    private String orderBy = "name";
    private String direction = "asc";
}

package com.epam.esm.dto;

import lombok.*;

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

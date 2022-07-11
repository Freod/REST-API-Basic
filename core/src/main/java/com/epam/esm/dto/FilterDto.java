package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class FilterDto {
    private final String tag;
    private final String name;
    private final String description;
    private final String orderBy;
    private final String direction;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public FilterDto(@JsonProperty("tag") String tag,
                     @JsonProperty("name") String name,
                     @JsonProperty("description") String description,
                     @JsonProperty("orderBy") String orderBy,
                     @JsonProperty("direction") String direction) {
        this.tag = tag;
        this.name = name;
        this.description = description;
        this.orderBy = orderBy;
        this.direction = direction;
    }
}

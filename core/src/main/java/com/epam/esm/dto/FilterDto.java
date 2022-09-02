package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class FilterDto {
    private final Set<TagDto> tags;
    private final String name;
    private final String description;
    private final String orderBy;
    private final String direction;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public FilterDto(@JsonProperty("tags") Set<TagDto> tags,
                     @JsonProperty("name") String name,
                     @JsonProperty("description") String description,
                     @JsonProperty("orderBy") String orderBy,
                     @JsonProperty("direction") String direction) {
        this.tags = tags != null ? tags : new HashSet<>();
        this.name = name;
        this.description = description;
        this.orderBy = orderBy;
        this.direction = direction;
    }

    public Set<TagDto> getTags() {
        return new HashSet<>(tags);
    }
}

package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigInteger;

@Getter
@EqualsAndHashCode
public class TagDto {
    private final BigInteger id;
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public TagDto(@JsonProperty("id") BigInteger id,
                  @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }
}

package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class UserDto extends RepresentationModel {
    private final Long id;
    private final String username;
    private final List<OrderDto> orders;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserDto(@JsonProperty("id") Long id,
                   @JsonProperty("username") String username,
                   @JsonProperty("orders") List<OrderDto> orders) {
        this.id = id;
        this.username = username;
        this.orders = orders == null ? new ArrayList<>() : orders;
    }
}

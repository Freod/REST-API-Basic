package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
    private Set<Tag> tags;
    private String name;
    private String description;
    private String orderBy;
    private String direction;

    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }
}

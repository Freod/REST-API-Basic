package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
// TODO: 04.07.2022
@NoArgsConstructor
public class Filter {
    private String tag = "";
    private String name = "";
    private String description = "";
}

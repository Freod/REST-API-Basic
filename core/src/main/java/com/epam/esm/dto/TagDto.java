package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    private BigInteger id;
    private String name;
}

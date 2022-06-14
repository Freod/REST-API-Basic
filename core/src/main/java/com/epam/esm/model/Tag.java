package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Tag {
    private BigInteger id;
    @NonNull
    private String name;
}

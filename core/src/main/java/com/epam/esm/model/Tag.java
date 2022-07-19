package com.epam.esm.model;

import lombok.*;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Tag {
    private BigInteger id;
    @NonNull
    private String name;
}

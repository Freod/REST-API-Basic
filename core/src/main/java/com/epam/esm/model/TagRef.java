package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class TagRef {
    BigInteger certificateId;
    BigInteger tagId;
    String tagName;
}

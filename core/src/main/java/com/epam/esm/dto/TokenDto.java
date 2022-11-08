package com.epam.esm.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class TokenDto implements Serializable {
    String type;
    String token;
}

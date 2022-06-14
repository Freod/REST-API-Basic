package com.epam.esm.model;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class GiftCertificate {
    private BigInteger id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}

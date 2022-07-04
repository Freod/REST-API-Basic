package com.epam.esm.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class GiftCertificateDto {
    private BigInteger id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagDto> tags = new ArrayList<>();
}

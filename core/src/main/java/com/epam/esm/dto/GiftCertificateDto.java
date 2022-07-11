package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class GiftCertificateDto {
    private final BigInteger id;
    private final String name;
    private final String description;
    private final Double price;
    private final Integer duration;
    private final String createDate;
    private final String lastUpdateDate;
    private final List<TagDto> tags;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public GiftCertificateDto(@JsonProperty("id") BigInteger id,
                              @JsonProperty("name") String name,
                              @JsonProperty("description") String description,
                              @JsonProperty("price") Double price,
                              @JsonProperty("duration") Integer duration,
                              @JsonProperty("createDate") String createDate,
                              @JsonProperty("lastUpdateDate") String lastUpdateDate,
                              @JsonProperty("tags") List<TagDto> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public List<TagDto> getTags() {
        return new ArrayList<>(tags);
    }
}

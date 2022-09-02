package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class OrderDto {
    private final Long id;
    private final List<GiftCertificateDto> giftCertificates;
    private final Double cost;
    private final LocalDateTime purchaseDate;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public OrderDto(@JsonProperty("id") Long id,
                    @JsonProperty("giftCertificates") List<GiftCertificateDto> giftCertificates,
                    @JsonProperty("cost") Double cost,
                    @JsonProperty("purchaseDate") LocalDateTime purchaseDate) {
        this.id = id;
        this.giftCertificates = giftCertificates;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
    }

    public List<GiftCertificateDto> getGiftCertificates() {
        return new ArrayList<>(giftCertificates);
    }
}

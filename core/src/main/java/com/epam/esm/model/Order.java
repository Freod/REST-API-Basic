package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<GiftCertificate> giftCertificates;
    private Double cost;
    private LocalDateTime purchaseDate;

    public Order(Long id, List<GiftCertificate> giftCertificates, Double cost, LocalDateTime purchaseDate) {
        this.id = id;
        this.giftCertificates = giftCertificates;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
    }
}

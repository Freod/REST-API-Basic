package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @OneToMany
    private List<Order> orders =  new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public User(Long id, String username, List<Order> orders) {
        this.id = id;
        this.username = username;
        this.orders = orders;
    }

    public void addOrder(Order order){
        orders.add(order);
    }
}

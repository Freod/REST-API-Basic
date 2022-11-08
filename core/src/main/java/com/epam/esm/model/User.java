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
    @Column(unique = true)
    private String username;
    private String password;
    private String roles;
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

    public User(Long id, String username, String password, String roles, List<Order> orders) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.orders = orders;
    }

    public void addOrder(Order order){
        orders.add(order);
    }
}

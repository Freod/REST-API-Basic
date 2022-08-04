package com.epam.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(unique = true)
    private String name;

    public Tag(@NonNull String name) {
        this.name = name;
    }

    public Tag(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }
}

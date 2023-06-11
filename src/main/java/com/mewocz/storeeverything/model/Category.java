package com.mewocz.storeeverything.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 3, max = 20, message = "The category anme should be between 3 adn 20 characters")
    private String name;

    @OneToMany(mappedBy = "category",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Information> informations;

    public Category(String id, String name) {
        this.id = Long.valueOf(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }


}

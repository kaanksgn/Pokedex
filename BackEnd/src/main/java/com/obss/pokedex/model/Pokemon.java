package com.obss.pokedex.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@SequenceGenerator(name = "pokeSeq", initialValue = 3001, allocationSize = 1)
public class Pokemon extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pokeSeq")
    private Long id;

    private String name;

    private String type;

    private String description;

    private String image;

    private String url;
    @Builder.Default
    @ManyToMany(mappedBy = "wishListedPokemons")
    @JsonBackReference
    private List<User> wishlistedUsers = new ArrayList<>();
    @Builder.Default
    @ManyToMany(mappedBy = "catchListedPokemons")
    @JsonBackReference
    private List<User> catchlistedUsers = new ArrayList<>();

}

package com.obss.pokedex.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;


@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@SequenceGenerator(name = "userSeq", initialValue = 1001, allocationSize = 1)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    private Long id;

    private String name;

    private String surname;

    @Column(unique = true)
    private String username;

    private String password;

    private boolean enabled;
    @NotBlank
    private String email;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Role> roles;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JsonManagedReference
    private List<Pokemon> catchListedPokemons;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JsonManagedReference
    private List<Pokemon> wishListedPokemons;


}

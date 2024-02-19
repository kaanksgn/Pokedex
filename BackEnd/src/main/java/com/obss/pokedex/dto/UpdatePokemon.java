package com.obss.pokedex.dto;

import lombok.Data;

@Data
public class UpdatePokemon {
    private Long id;
    private String name;
    private String type;
    private String description;
}

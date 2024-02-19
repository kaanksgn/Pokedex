package com.obss.pokedex.dto;

import lombok.Data;

@Data
public class RequestNewPokemon {
    private String name;
    private String type;
    private String description;

}

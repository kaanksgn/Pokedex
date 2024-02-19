package com.obss.pokedex.dto;

import com.obss.pokedex.model.Pokemon;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String fullName;
    private String username;
    private Boolean isAdmin;
    private List<Pokemon> wishlist;
    private List<Pokemon> catchlist;


}

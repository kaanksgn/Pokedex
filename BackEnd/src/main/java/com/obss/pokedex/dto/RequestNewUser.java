package com.obss.pokedex.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestNewUser {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 25)
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(min = 3, max = 25)
    private String surname;

    private String email;

    private String username;

    private Boolean isAdmin;

    private String password;
}

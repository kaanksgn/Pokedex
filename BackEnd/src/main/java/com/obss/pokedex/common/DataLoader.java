package com.obss.pokedex.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obss.pokedex.model.Pokemon;
import com.obss.pokedex.service.PokeService;
import com.obss.pokedex.service.RoleService;
import com.obss.pokedex.service.UserService;
import com.obss.pokedex.common.Constants.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class DataLoader implements ApplicationRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final PokeService pokeService;
    ObjectMapper mapper = new ObjectMapper();
    //Pokemon poke = mapper.readValue()


    @Override
    public void run(ApplicationArguments args) throws Exception {
        roleService.checkAndCreateRoles(List.of(Roles.ADMIN, Roles.USER));
        userService.checkAndCreateAdminUser();
        userService.checkAndCreateUser();
        pokeService.createPokemon();
    }


}

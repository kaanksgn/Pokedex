package com.obss.pokedex.controller;


import com.obss.pokedex.common.Constants;
import com.obss.pokedex.dto.*;
import com.obss.pokedex.exception.PokeNotFoundException;
import com.obss.pokedex.exception.UserNotFoundException;
import com.obss.pokedex.mapper.UserMapper;
import com.obss.pokedex.model.Pokemon;
import com.obss.pokedex.model.Role;
import com.obss.pokedex.model.User;
import com.obss.pokedex.repository.RoleRepository;
import com.obss.pokedex.security.UserAuthDetails;
import com.obss.pokedex.service.PokeService;
import com.obss.pokedex.service.RoleService;
import com.obss.pokedex.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Example;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admindashboard")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final PokeService pokeService;
    private final RoleService roleService;

    @GetMapping
    public BaseResponse<UserDto> loginSuccessfull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserAuthDetails obj = (UserAuthDetails) auth.getPrincipal();
        User user = (User) obj.getUser();
        return new BaseResponse<>(UserMapper.mapToDto(user));
    }

    @GetMapping("/list_all")
    public BaseResponse<List<UserDto>> getEveryone() {
        List<User> users = userService.findAll();
        return new BaseResponse<>(UserMapper.mapToDto(users));
    }

    //==================================//
    // Admin Operations for Admin
    //==================================//

    @GetMapping("/admin_list")
    public BaseResponse<List<UserDto>> findAdmins(@RequestParam(value = "id", required = false) Long id,
                                                  @RequestParam(value = "name", required = false) String name,
                                                  @RequestParam(value = "surname", required = false) String surname,
                                                  @RequestParam(value = "username", required = false) String username) {
        if (userService.listUsersBy(id, name, surname, username).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            List<User> returned = userService.listUsersBy(id, name, surname, username)
                    .stream()
                    .filter(user -> user.getRoles()
                            .stream()
                            .anyMatch(r -> r.getName()
                                    .equals(Constants.Roles.USER))).toList();
            return new BaseResponse<>(UserMapper.mapToDto(returned));
        }


    }

    @PostMapping("/admin_add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<UserDto> addAdmin(@Valid @RequestBody RequestNewUser user) {
        final User newUser = this.userService.createNewAdmin(User
                .builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .username(user.getUsername())
                .build(), user.getPassword());
        return new BaseResponse<>(UserMapper.mapToDto(newUser));

    }

    @PostMapping("/admin_delete")
    public String deleteAdmin(@RequestParam(value = "adminId") Long adminId) {
        userService.deleteUser(adminId);

        return "Admin " + adminId + " is deleted. Check database.";
    }

    //==================================//
    // User Operations for Admin
    //==================================//

    // List all Users

    @GetMapping("/user_list")
    public BaseResponse<List<UserDto>> findUsers(@RequestParam(value = "id", required = false) Long id,
                                                 @RequestParam(value = "name", required = false) String name,
                                                 @RequestParam(value = "surname", required = false) String surname,
                                                 @RequestParam(value = "username", required = false) String username) {
        if (userService.listUsersBy(id, name, surname, username).isEmpty()) {
            return new BaseResponse<>(Collections.emptyList());
        } else {
            List<User> returned = userService.listUsersBy(id, name, surname, username)
                    .stream()
                    .filter(user -> user.getRoles()
                            .stream()
                            .anyMatch(r -> r.getName()
                                    .equals(Constants.Roles.USER))).toList();
            return new BaseResponse<>(UserMapper.mapToDto(returned));
        }


    }

    @PostMapping("/user_add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<UserDto> addUser(@Valid @RequestBody RequestNewUser user) {
        final User newUser = this.userService.createNewUser(User
                .builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .username(user.getUsername())
                .build(), user.getPassword());
        return new BaseResponse<>(UserMapper.mapToDto(newUser));
    }

    @PostMapping("/user_update")
    public int updateUser(@RequestBody UpdateUser user) {
        return userService.updateUser(user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getId());

    }

    @PostMapping("/user_delete")
    public String deleteUser(@RequestParam(value = "userId") Long userId) {
        userService.deleteUser(userId);

        return "User " + userId + " is deleted. Check database.";
    }


    //==================================//
    // Pokemon Operations for Admin
    //==================================//

    // List Pokemons
    @GetMapping("/poke_list")
    public List<Pokemon> getPokemons(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "type", required = false) String type,
                                     @RequestParam(value = "description", required = false) String description) {
        Pokemon poke = new Pokemon();
        poke.setName(name);
        poke.setType(type);
        poke.setDescription(description);
        return pokeService.findAll();
    }

    @GetMapping("/poke_listby")
    public List<Pokemon> listBy(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "type", required = false) String type
    ) {
        if (pokeService.listPokemonsBy(name, type).isEmpty()) {
            return Collections.emptyList();
        }
        ;
        return pokeService.listPokemonsBy(name, type);
    }

    // Add Pokemon to the Database
    @PostMapping("/poke_add")
    public Pokemon addPokemon(@RequestBody RequestNewPokemon pokemon) {
        final Pokemon poke = this.pokeService.createNewPokemon(Pokemon
                .builder()
                .name(pokemon.getName())
                .type(pokemon.getType())
                .description(pokemon.getDescription())
                .catchlistedUsers(Collections.emptyList())
                .wishlistedUsers(Collections.emptyList())
                .build());

        return poke;
    }

    // Delete Pokemon from the Database
    @PostMapping("/poke_delete")
    public String deletePokemon(@RequestParam(value = "pokeId") Long pokeId) {
        pokeService.deletePokemon(pokeId);

        return "Pokemon " + pokeId + " is deleted. Check database.";


    }

    @PostMapping("/poke_update")
    public int updatePokemon(@RequestBody UpdatePokemon poke) {
        return pokeService.updatePokemon(poke.getName(), poke.getType(), poke.getDescription(), poke.getId());

    }

   /* @PatchMapping("/poke_update")
    public String updatePokemon(@RequestParam(value = "pokeId") Long pokeId,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "type", required = false) String type,
                                @RequestParam(value = "description", required = false) String description) {
        Optional<Pokemon> poke = Optional.ofNullable(pokeService.findById(pokeId));
        if (poke.isPresent()) {


        } else {
            return "Pokemon with ID " + pokeId + " is not present.";
        }
        return "null";
    }*/
}

package com.obss.pokedex.controller;

import com.obss.pokedex.dto.BaseResponse;
import com.obss.pokedex.dto.RequestNewUser;
import com.obss.pokedex.dto.UserDto;
import com.obss.pokedex.exception.PokeNotFoundException;
import com.obss.pokedex.exception.UserNotFoundException;
import com.obss.pokedex.mapper.UserMapper;
import com.obss.pokedex.model.Pokemon;
import com.obss.pokedex.model.User;
import com.obss.pokedex.security.UserAuthDetails;
import com.obss.pokedex.service.PokeService;
import com.obss.pokedex.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/userdashboard")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PokeService pokeService;

    @GetMapping
    public BaseResponse<UserDto> loginSuccessfull() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserAuthDetails obj = (UserAuthDetails) auth.getPrincipal();
        User user = (User) obj.getUser();
        return new BaseResponse<>(UserMapper.mapToDto(user));
    }


    /*  @PostMapping("/add_poke_to_wishlist")
      @PreAuthorize("hasRole('ROLE_ADMIN')")
      public BaseResponse<UserDto> addPokemonToWishList(@Valid @RequestParam(value = "pokeId") Integer pokeid) {

          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          Object name = auth.getPrincipal();
          UserAuthDetails authDetails = (UserAuthDetails) name;
          String username = authDetails.getUsername();
          Long pokeId = pokeid.longValue();
          User user = userService.findByUsername(username).orElseThrow(UserNotFoundException::new);
          userService.addPokemonToWishList(user.getId(), pokeId);
          return new BaseResponse<>(UserMapper.mapToDto(user));
      }*/
    @GetMapping("/poke_list")
    public List<Pokemon> getPokemons() {
        return pokeService.findAll();
    }

    @GetMapping("/poke_listby")
    public List<Pokemon> listBy(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "type", required = false) String type
    ) {
        if (pokeService.listPokemonsBy(name, type).isEmpty()) {
            throw new PokeNotFoundException();
        }
        ;
        return pokeService.listPokemonsBy(name, type);
    }


    @GetMapping("/wishlisted_poke")
    public List<Pokemon> getWishlistedPokemons() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object object = auth.getPrincipal();
        UserAuthDetails authDetails = (UserAuthDetails) object;
        Long userId = authDetails.getUser().getId();
        return pokeService.findWishlistedPokemons(userId);
    }

    @PostMapping("/remove_from_wishlist")
    public BaseResponse<String> removeFromWishList(@RequestParam(value = "pokeId") Integer str) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object object = auth.getPrincipal();
        UserAuthDetails authDetails = (UserAuthDetails) object;
        Long userEdited = authDetails.getUser().getId();
        Long pokeId = str.longValue();
        userService.removePokemonFromWishList(userEdited, pokeId);

        return new BaseResponse<>(200L, "Removed From Wishlist");
    }

    @PostMapping("/remove_from_catchlist")
    public BaseResponse<String> removeFromCatchList(@RequestParam(value = "pokeId") Integer str) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object object = auth.getPrincipal();
        UserAuthDetails authDetails = (UserAuthDetails) object;
        Long userEdited = authDetails.getUser().getId();
        Long pokeId = str.longValue();
        userService.removePokemonFromCatchList(userEdited, pokeId);

        return new BaseResponse<>(200L, "Removed From CatchList");
    }

    @GetMapping("/catchlisted_poke")
    public List<Pokemon> getCatchlistedPokemons() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object object = auth.getPrincipal();
        UserAuthDetails authDetails = (UserAuthDetails) object;
        Long userId = authDetails.getUser().getId();
        return pokeService.findCatchlistedPokemons(userId);
    }

    @PostMapping("/poke_add_to_lists")
    public BaseResponse<UserDto> addPokemonToLists(@Valid @RequestParam(value = "pokeId") Integer pokeid,
                                                   @RequestParam(value = "which") String which) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object object = auth.getPrincipal();
        UserAuthDetails authDetails = (UserAuthDetails) object;
        String username = authDetails.getUsername();
        Long pokeId = pokeid.longValue();
        Long userId = authDetails.getUser().getId();
        List<Pokemon> wishlisted = pokeService.findWishlistedPokemons(userId);
        List<Pokemon> catchlisted = pokeService.findCatchlistedPokemons(userId);
        User user = userService.findByUsername(username).orElseThrow(UserNotFoundException::new);
        switch (which) {
            case "catch":
                if (catchlisted.stream().anyMatch(poke -> poke.getId().equals(pokeId))) {
                    return new BaseResponse<>(202L, "Already Catchlisted");

                } else {
                    userService.addPokemonToCatchList(user.getId(), pokeId);
                }


                break;

            case "wish":
                if (wishlisted.stream().anyMatch(poke -> poke.getId().equals(pokeId))) {
                    return new BaseResponse<>(202L, "Already Wishlisted");

                } else {
                    userService.addPokemonToWishList(user.getId(), pokeId);
                }
                break;
        }
        return new BaseResponse<>(UserMapper.mapToDto(user));
    }


}

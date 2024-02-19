package com.obss.pokedex.mapper;

import com.obss.pokedex.common.Constants;
import com.obss.pokedex.dto.UserDto;
import com.obss.pokedex.model.User;
import com.obss.pokedex.util.CollectionUtils;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class UserMapper {
    public static final UserDto mapToDto(User user) {
        final UserDto dto = new UserDto();
        if (Objects.isNull(user)) {
            return dto;
        }
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getName() + " " + user.getSurname());

        dto.setIsAdmin((user.getRoles().stream().anyMatch(role -> role.getName().equals(Constants.Roles.ADMIN))));
        if (!dto.getIsAdmin()) {
            dto.setWishlist(user.getWishListedPokemons());
            dto.setCatchlist(user.getCatchListedPokemons());
        }
        return dto;
    }

    public static final List<UserDto> mapToDto(List<User> users) {
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }
        return users.stream().map(user -> mapToDto(user)).toList();
    }
}

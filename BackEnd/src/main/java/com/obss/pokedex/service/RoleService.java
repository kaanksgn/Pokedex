package com.obss.pokedex.service;

import com.obss.pokedex.model.Role;

import java.util.List;

public interface RoleService {

    void checkAndCreateRoles(List<String> roleNames);

    Role findByName(String roleName);
}

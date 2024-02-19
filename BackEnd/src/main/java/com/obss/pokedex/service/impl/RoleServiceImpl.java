package com.obss.pokedex.service.impl;

import com.obss.pokedex.exception.RoleNotFoundException;
import com.obss.pokedex.model.Role;
import com.obss.pokedex.repository.RoleRepository;
import com.obss.pokedex.service.RoleService;
import com.obss.pokedex.util.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    @Override
    public void checkAndCreateRoles(List<String> roleNames) {
        if (CollectionUtils.isEmpty(roleNames)) {
            return;
        }
        roleNames.stream().forEach(roleName -> {
            roleRepository
                    .findByName(roleName)
                    .orElseGet(() ->
                            roleRepository
                                    .save(Role
                                            .builder()
                                            .name(roleName)
                                            .build()));

        });
    }

    @Override
    public Role findByName(String roleName) {
        Objects.requireNonNull(roleName, "Role Name cannot be null");
        return roleRepository.findByName(roleName).orElseThrow(RoleNotFoundException::new);
    }
}

package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.repository.RoleRepository;
import com.softserve.academy.studhub.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(RoleName roleName) {

        Optional<Role> role = roleRepository.findByName(roleName);

        if (role.isPresent()) {
            return role.get();
        }
        throw new IllegalArgumentException("Role is not found by this name!");
    }
}

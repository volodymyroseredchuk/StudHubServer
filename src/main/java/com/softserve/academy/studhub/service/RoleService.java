package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.enums.RoleName;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findByName(RoleName roleName);
}

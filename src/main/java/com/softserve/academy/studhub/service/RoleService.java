package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.enums.RoleName;

public interface RoleService {

    Role findByName(RoleName roleName);
}

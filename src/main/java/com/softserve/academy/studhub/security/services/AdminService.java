package com.softserve.academy.studhub.security.services;

import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.RoleName;

public interface AdminService {

    User addRole(Integer userId, RoleName roleName);

    User removeRole(Integer userId, RoleName roleName);
}

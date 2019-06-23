package com.softserve.academy.studhub.security.services;

import com.softserve.academy.studhub.entity.enums.RoleName;

public interface AdminService {

    void addRole(Integer userId, RoleName roleName);

    void removeRole(Integer userId, RoleName roleName);
}

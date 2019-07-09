package com.softserve.academy.studhub.config;


import com.softserve.academy.studhub.dto.UserDTO;
import com.softserve.academy.studhub.entity.Privilege;
import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.print.attribute.standard.Destination;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class ApplicationConfig {

    @Bean(name = "modelMapper")
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<User, UserDTO> userTypeMap = modelMapper.createTypeMap(User.class, UserDTO.class);
        /*
        userTypeMap.addMapping(src -> {
            Set<Privilege> privileges = new HashSet<>();
            Set<Role> roles = src.getRoles();

            for (Role role:
                roles) {

            if(role.getPrivileges() == null){
                return new HashSet<>();
            }
            for(Privilege privilege:
                    role.getPrivileges()){
                privileges.add(privilege);
            }

        }

        if(src == null){
            return new HashSet<>();
        }
        Set<Privilege> privileges = new HashSet<>();
        Set<Role> roles = src.getRoles();
        for (Role role:
                roles) {
            privileges.addAll(role.getPrivileges());
        }
        return privileges;

        return privileges;
    }, UserDTO::setPrivileges);
            */
        return modelMapper;
    }

}


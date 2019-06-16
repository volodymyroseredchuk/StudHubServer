package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.dto.UserDto;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User add(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public UserDto get(Integer id) {
        Optional<User> resultVote = userRepository.findById(id);
        if (resultVote.isPresent()) {

            User user = resultVote.get();

            return createUserDto(user);
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }

    @Override
    public User update(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto findByUsername(String username) {
        Optional<User> resultVote = userRepository.findByUsername(username);
        if (resultVote.isPresent()) {
            User user = resultVote.get();

            return createUserDto(user);
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }

    private UserDto createUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setCreationDate(user.getCreationDate());
        userDto.setUniversity(user.getUniversity());
        userDto.setImageUrl(user.getImageUrl());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}

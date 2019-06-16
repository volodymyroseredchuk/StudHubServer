package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.dto.UserDTO;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public UserDTO add(User user) {

        return modelMapper.map(userRepository.saveAndFlush(user), UserDTO.class);
    }

    @Override
    public UserDTO get(Integer id) {

        Optional<User> resultVote = userRepository.findById(id);

        if (resultVote.isPresent()) {

            User user = resultVote.get();

            return modelMapper.map(user, UserDTO.class);
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }

    @Override
    public UserDTO update(User user) {

        return modelMapper.map(userRepository.saveAndFlush(user), UserDTO.class);
    }

    @Override
    public void delete(Integer id) {

        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> getAll() {

        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUsername(String username) {

        Optional<User> resultVote = userRepository.findByUsername(username);

        if (resultVote.isPresent()) {

            User user = resultVote.get();

            return modelMapper.map(user, UserDTO.class);
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }

}

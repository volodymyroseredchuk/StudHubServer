package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@AllArgsConstructor
@Value
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User add(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public Optional<User> get(Integer id) {
        return userRepository.findById(id);
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

}

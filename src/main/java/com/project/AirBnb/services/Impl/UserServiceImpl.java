package com.project.AirBnb.services.Impl;

import com.project.AirBnb.entities.User;
import com.project.AirBnb.exceptions.ResourceNotFoundException;
import com.project.AirBnb.repositories.UserRepository;
import com.project.AirBnb.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        //todo - need modification :: use UserDTO
        log.info("Getting user with id : {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id : "+ id));
        return user;
    }
}

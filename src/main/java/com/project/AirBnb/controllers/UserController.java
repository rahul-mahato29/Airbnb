package com.project.AirBnb.controllers;

import com.project.AirBnb.dto.UserDTO;
import com.project.AirBnb.entities.User;
import com.project.AirBnb.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> getUserDetailsById(@PathVariable(name = "userId") Long id) {
        UserDTO user = userService.getUserDetailsById(id);
        return ResponseEntity.ok(user);
    }
}

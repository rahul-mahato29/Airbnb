package com.project.AirBnb.services;

import com.project.AirBnb.dto.UserDTO;
import com.project.AirBnb.entities.User;

public interface UserService {
    User getUserById(Long id);

    UserDTO getUserDetailsById(Long id);
}

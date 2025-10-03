package com.project.AirBnb.security;

import com.project.AirBnb.dto.LoginDTO;
import com.project.AirBnb.dto.LoginResponseDTO;
import com.project.AirBnb.dto.SignUpDTO;
import com.project.AirBnb.dto.UserDTO;
import com.project.AirBnb.entities.User;
import com.project.AirBnb.entities.enums.Role;
import com.project.AirBnb.repositories.UserRepository;
import com.project.AirBnb.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;
    private final UserRepository userRepository;

    public UserDTO signUp(SignUpDTO singUpDTO) {
        Optional<User> user = userRepository.findByEmail(singUpDTO.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with this email already exist : "+singUpDTO.getEmail());
        }

        User toBeCreatedUser = modelMapper.map(singUpDTO, User.class);
        toBeCreatedUser.setRoles(Set.of(Role.GUEST));
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));
        User savedUser = userRepository.save(toBeCreatedUser);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    public LoginResponseDTO logIn(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String accessToken =  jwtService.generateAccessToken(user);
        String refreshToken =  jwtService.generateRefreshToken(user);
        sessionService.generateNewSession(user, refreshToken);

        return new LoginResponseDTO(user.getId(), accessToken, refreshToken);
    }


    //we will use refreshToken to generate access token
    public LoginResponseDTO refreshToken(String refreshToken) {
        //first validate the refresh token
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        sessionService.validateSession(refreshToken);
        User user = userService.getUserById(userId);

        String accessToken =  jwtService.generateAccessToken(user);
        return new LoginResponseDTO(user.getId(), accessToken, refreshToken);
    }
}
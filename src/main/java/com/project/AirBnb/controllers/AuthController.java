package com.project.AirBnb.controllers;

import com.project.AirBnb.dto.LoginDTO;
import com.project.AirBnb.dto.LoginResponseDTO;
import com.project.AirBnb.dto.SignUpDTO;
import com.project.AirBnb.dto.UserDTO;
import com.project.AirBnb.security.AuthService;
import com.project.AirBnb.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

//    @Value("${deploy.env}")
    public String deployEnv = "Development";

    @PostMapping(path = "/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO singUpDTO) {
        UserDTO user = authService.signUp(singUpDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDTO> logIn(@RequestBody LoginDTO loginDTO, HttpServletRequest request,
                                                  HttpServletResponse response) {
        LoginResponseDTO loginResponseDTO = authService.logIn(loginDTO);

        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));  //now cookies will pass through only HTTPS (S - security layer) in prod-env
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token Not Found Inside The Cookies"));

        LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(loginResponseDTO);
    }


    //Homework : Implement logout controller, and with this implementation also delete session.
}

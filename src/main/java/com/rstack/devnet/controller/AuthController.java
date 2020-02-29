package com.rstack.devnet.controller;

import com.rstack.devnet.service.IAuthService;
import com.rstack.devnet.utility.LoginRequest;
import com.rstack.devnet.utility.LoginResponse;
import com.rstack.devnet.utility.RegisterRequest;
import com.rstack.devnet.utility.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IAuthService authService;

    @PostMapping(value = "/login")
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (AuthenticationException e) {
            throw new Exception("Incorrect username password", e);
        }

        return authService.loginUser(loginRequest);
    }

    @PostMapping(value = "/register")
    public RegisterResponse registerUser(@RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }
}

package com.rstack.devnet.serviceImpl;

import com.rstack.devnet.model.USER;
import com.rstack.devnet.repository.UserRepository;
import com.rstack.devnet.service.IAuthService;
import com.rstack.devnet.utility.*;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        LoginResponse loginResponse;

        USER user = userRepository.loginUser(loginRequest);

        if(user != null){
            loginResponse = new LoginResponse("SUCCESS", user.getUsername());
        }

        else {
            loginResponse = new LoginResponse("FAILED LOGIN", user.getUsername());
        }
        return loginResponse;
    }

    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        return null;
    }
}

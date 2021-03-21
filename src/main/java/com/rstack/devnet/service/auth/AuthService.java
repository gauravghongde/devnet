package com.rstack.devnet.service.auth;

import com.rstack.devnet.utility.LoginRequest;
import com.rstack.devnet.utility.LoginResponse;
import com.rstack.devnet.utility.RegisterRequest;
import com.rstack.devnet.utility.RegisterResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    LoginResponse loginUser(LoginRequest loginRequest, Authentication authentication);

    RegisterResponse registerUser(RegisterRequest registerRequest);
}

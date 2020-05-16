package com.rstack.devnet.service.auth;

import com.rstack.devnet.utility.LoginRequest;
import com.rstack.devnet.utility.LoginResponse;
import com.rstack.devnet.utility.RegisterRequest;
import com.rstack.devnet.utility.RegisterResponse;

public interface AuthService {
    LoginResponse loginUser(LoginRequest loginRequest);

    RegisterResponse registerUser(RegisterRequest registerRequest);
}

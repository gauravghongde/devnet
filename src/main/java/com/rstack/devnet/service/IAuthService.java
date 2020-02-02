package com.rstack.devnet.service;

import com.rstack.devnet.utility.*;

public interface IAuthService {
    LoginResponse loginUser(LoginRequest loginRequest);
    RegisterResponse registerUser(RegisterRequest registerRequest);
}

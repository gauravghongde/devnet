package com.rstack.devnet.serviceImpl;

import com.rstack.devnet.model.USER;
import com.rstack.devnet.repository.UserRepository;
import com.rstack.devnet.service.IAuthService;
import com.rstack.devnet.utility.LoginRequest;
import com.rstack.devnet.utility.LoginResponse;
import com.rstack.devnet.utility.RegisterRequest;
import com.rstack.devnet.utility.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        LoginResponse loginResponse;

        USER user = userRepository.loginUser(loginRequest);

        if (user != null) {
            loginResponse = new LoginResponse("SUCCESS", user.getUsername());
        } else {
            loginResponse = new LoginResponse("FAILED LOGIN", user.getUsername());
        }
        return loginResponse;
    }

    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        RegisterResponse registerResponse;
        boolean isUsernamePresent = userRepository.checkIfUsernameExists(registerRequest.getUsername());
        boolean isEmailIdPresent = userRepository.checkIfUsernameExists(registerRequest.getEmailId());
        if (!isUsernamePresent && !isEmailIdPresent) {
            try {
                USER user = userRepository.registerUser(registerRequest);
                registerResponse = new RegisterResponse("SUCCESS!! REGISTRATION DONE", user.getUsername());
            } catch (Exception e) {
                registerResponse = new RegisterResponse("FAILED IN INSERT", null);
            }
        } else {
            String failResponse = "Cannot register,";
            failResponse = isUsernamePresent ? failResponse + "\n - the Username -> " + registerRequest.getUsername() + " is already present" : failResponse;
            failResponse = isEmailIdPresent ? failResponse + "\n - the EmailId -> " + registerRequest.getEmailId() + " is already present" : failResponse;
            registerResponse = new RegisterResponse(failResponse, null);
        }
        return registerResponse;
    }
}

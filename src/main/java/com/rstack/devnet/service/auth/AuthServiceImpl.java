package com.rstack.devnet.service.auth;

import com.rstack.devnet.model.User;
import com.rstack.devnet.repository.UserRepository;
import com.rstack.devnet.security.JwtTokenProvider;
import com.rstack.devnet.security.UserPrincipal;
import com.rstack.devnet.utility.LoginRequest;
import com.rstack.devnet.utility.LoginResponse;
import com.rstack.devnet.utility.RegisterRequest;
import com.rstack.devnet.utility.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Override
    public LoginResponse loginUser(LoginRequest loginRequest, Authentication authentication) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        String token = jwtTokenProvider.createToken(user);
        return new LoginResponse(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), token);
    }

    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        RegisterResponse registerResponse;
        boolean isUsernamePresent = userRepository.checkIfUsernameExists(registerRequest.getUsername());
        boolean isEmailIdPresent = userRepository.checkIfUsernameExists(registerRequest.getEmail());
        if (!isUsernamePresent && !isEmailIdPresent) {
            try {
                User user = userRepository.registerUser(registerRequest);
                registerResponse = new RegisterResponse("SUCCESS!! REGISTRATION DONE", user.getUsername());
            } catch (Exception e) {
                registerResponse = new RegisterResponse("FAILED IN INSERT", null);
            }
        } else {
            String failResponse = "Cannot register,";
            failResponse = isUsernamePresent ? failResponse + "\n - the Username -> " + registerRequest.getUsername() + " is already present" : failResponse;
            failResponse = isEmailIdPresent ? failResponse + "\n - the EmailId -> " + registerRequest.getEmail() + " is already present" : failResponse;
            registerResponse = new RegisterResponse(failResponse, null);
        }
        return registerResponse;
    }
}

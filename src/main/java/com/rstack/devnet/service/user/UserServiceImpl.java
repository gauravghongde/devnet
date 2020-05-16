package com.rstack.devnet.service.user;

import com.rstack.devnet.dto.model.UserDTO;
import com.rstack.devnet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getUserProfile(String username) {
        return userRepository.getUserProfile(username);
    }
}

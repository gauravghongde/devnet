package com.rstack.devnet.service.user;

import com.rstack.devnet.dto.model.UserDTO;

public interface UserService {
    UserDTO getUserProfile(String username);
}

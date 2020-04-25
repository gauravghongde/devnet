package com.rstack.devnet.dto.mapper;

import com.rstack.devnet.dto.model.UserDTO;
import com.rstack.devnet.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private static final Logger LOG = LoggerFactory.getLogger(UserMapper.class);

    public UserDTO toUserDTO(User user) {
        LOG.info("Converting User Entity to User DTO");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setEncryptedPassword(user.getEncryptedPassword());
        return userDTO;
    }
}

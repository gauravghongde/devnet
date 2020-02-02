package com.rstack.devnet.service;

import com.rstack.devnet.model.USER;
import com.rstack.devnet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        USER user = userRepository.findByUsernameOrEmail(username);
        //TODO: Handle UsernameNotFound Exception
        return new User(user.getUsername(), user.getEncryptedPassword(), new ArrayList<>());
    }
}

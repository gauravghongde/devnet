package com.rstack.devnet.controller;

import com.rstack.devnet.security.JwtTokenProvider;
import com.rstack.devnet.service.MyUserDetailsService;
import com.rstack.devnet.utility.AuthenticationRequest;
import com.rstack.devnet.utility.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class DevnetController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    ////////// USER /////
    @GetMapping(value = "/home/ping")
    public ResponseEntity<String> pingHome() {
        return ResponseEntity.ok().body("Hi Home");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Incorrect username password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenProvider.createToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}

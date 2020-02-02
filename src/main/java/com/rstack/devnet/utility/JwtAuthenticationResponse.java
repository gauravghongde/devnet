package com.rstack.devnet.utility;

public class JwtAuthenticationResponse {

    private String jwt;

    public JwtAuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}

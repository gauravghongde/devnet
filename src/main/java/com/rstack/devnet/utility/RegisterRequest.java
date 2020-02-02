package com.rstack.devnet.utility;

public class RegisterRequest {
    private String username;
    private String emailId;
    private String firstName;
    private String lastName;
    private String password;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String emailId, String firstName, String lastName, String password) {
        this.username = username;
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

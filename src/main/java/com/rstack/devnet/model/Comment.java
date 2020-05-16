package com.rstack.devnet.model;

import java.time.Instant;
import java.util.HashMap;

public class Comment {
    private String id;
    private String body;
    private String username;
    private Instant postedAt;
    //    private Boolean isEdited;
    private Vote vote;
    private HashMap<String,Integer> usersInteracted;

    public Comment() {
    }

    public Comment(String id, String body, String username, Instant postedAt, Vote vote, HashMap<String, Integer> usersInteracted) {
        this.id = id;
        this.body = body;
        this.username = username;
        this.postedAt = postedAt;
        this.vote = vote;
        this.usersInteracted = usersInteracted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Instant postedAt) {
        this.postedAt = postedAt;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public HashMap<String, Integer> getUsersInteracted() {
        return usersInteracted;
    }

    public void setUsersInteracted(HashMap<String, Integer> usersInteracted) {
        this.usersInteracted = usersInteracted;
    }
}

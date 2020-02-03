package com.rstack.devnet.model;

import java.time.LocalDate;

public class COMMENT {
    private String commentId;
    private String forContent; //AID/QID
    private String commentBody;
    private String byUser; //username
    private LocalDate postedAt;
    private int upVotes;
    private int downVotes;

}

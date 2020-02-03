package com.rstack.devnet.model;

import java.time.LocalDate;
import java.util.List;

public class ANSWER {
    private String answerId;
    private String forQuestion; //QID
    private String answerBody;
    private String byUser; //username
    private LocalDate postedAt;
    private int upVotes;
    private int downVotes;
    private List<COMMENT> commentObj;

}

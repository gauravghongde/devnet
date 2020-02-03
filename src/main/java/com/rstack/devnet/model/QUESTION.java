package com.rstack.devnet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "QUESTION")
public class QUESTION {
    @Id
    private String questionId;
    private String questionHeader;
    private String questionBody;
    private String byUser; //username
    private LocalDate postedAt;
    private int upVotes;
    private int downVotes;
    private List<ANSWER> answerObj;
    private List<COMMENT> commentObj;
}

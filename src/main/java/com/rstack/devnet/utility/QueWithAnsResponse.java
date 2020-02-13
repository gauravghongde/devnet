package com.rstack.devnet.utility;

import com.rstack.devnet.model.ANSWER;
import com.rstack.devnet.model.QUESTION;

import java.util.List;

public class QueWithAnsResponse {
    QUESTION question;
    List<ANSWER> listOfAnswers;

    public QueWithAnsResponse() {
    }

    public QueWithAnsResponse(QUESTION question, List<ANSWER> listOfAnswers) {
        this.question = question;
        this.listOfAnswers = listOfAnswers;
    }

    public QUESTION getQuestion() {
        return question;
    }

    public void setQuestion(QUESTION question) {
        this.question = question;
    }

    public List<ANSWER> getListOfAnswers() {
        return listOfAnswers;
    }

    public void setListOfAnswers(List<ANSWER> listOfAnswers) {
        this.listOfAnswers = listOfAnswers;
    }
}

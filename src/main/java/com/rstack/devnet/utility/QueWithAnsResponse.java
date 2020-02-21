package com.rstack.devnet.utility;

import com.rstack.devnet.model.POST;

import java.util.List;

public class QueWithAnsResponse {
    private POST question;
    private List<POST> listOfAnswers;

    public QueWithAnsResponse() {
    }

    public QueWithAnsResponse(POST question, List<POST> listOfAnswers) {
        this.question = question;
        this.listOfAnswers = listOfAnswers;
    }

    public POST getQuestion() {
        return question;
    }

    public void setQuestion(POST question) {
        this.question = question;
    }

    public List<POST> getListOfAnswers() {
        return listOfAnswers;
    }

    public void setListOfAnswers(List<POST> listOfAnswers) {
        this.listOfAnswers = listOfAnswers;
    }
}

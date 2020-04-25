package com.rstack.devnet.model;

public class Vote {
    private int upVotes;
    private int downVotes;
    private int voteStatus;

    public Vote() {
    }

    public Vote(int upVotes, int downVotes, int voteStatus) {
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.voteStatus = voteStatus;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public int getVoteStatus() {
        return voteStatus;
    }

    public void setVoteStatus(int voteStatus) {
        this.voteStatus = voteStatus;
    }
}

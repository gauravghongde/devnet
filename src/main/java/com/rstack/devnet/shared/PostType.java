package com.rstack.devnet.shared;

public enum PostType {
    QUESTION("q"),
    ANSWER("a"),
    COMMENT("c");

    private String idPrefix;

    PostType(String idPrefix) {
        this.idPrefix = idPrefix;
    }

    public String getIdPrefix() {
        return idPrefix;
    }

}

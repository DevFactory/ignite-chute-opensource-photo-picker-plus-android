package com.chute.sdk.v2_1.model.body;

public class CommentBodyRequestModel {

    private String commentText;
    private String name;
    private String email;

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override public String toString() {
        return "CommentBodyRequestModel{" +
                "commentText='" + commentText + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}



package com.just_cook.server.model;

public class Comment {
    Integer commentId;
    Integer userId;
    Integer recipeId;
    String comment;

    public Comment() {
        this.commentId = null;
        this.userId = null;
        this.recipeId = null;
        this.comment = null;
    }

    public Comment(Integer userId, Integer recipeId, String comment) {
        this.commentId = null;
        this.userId = userId;
        this.recipeId = recipeId;
        this.comment = comment;
    }

    public Comment(Integer commentId, Integer userId, Integer recipeId, String comment) {
        this.commentId = commentId;
        this.userId = userId;
        this.recipeId = recipeId;
        this.comment = comment;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCommentID() {
        return commentId;
    }

    public Integer getUserID() {
        return userId;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentID=" + commentId +
                ", userID=" + userId +
                ", recipeID=" + recipeId +
                ", comment='" + comment + '\'' +
                '}';
    }
}


package com.just_cook.server.service.interfaces;

import com.just_cook.server.model.Comment;

import java.util.List;

public interface CommentService extends DatabaseService{
    List<Comment> getRecipeComments(Integer id);
    Comment updateComment(Comment comment);
    List<Comment> getAllComments();
    Comment addNewCommment(Comment comment);
}

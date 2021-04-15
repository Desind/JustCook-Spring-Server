package com.just_cook.server.service;

import com.just_cook.server.model.Comment;
import com.just_cook.server.model.CookUser;
import com.just_cook.server.model.Rating;
import com.just_cook.server.model.Recipe;
import com.just_cook.server.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainDatabaseServiceImpl implements MainDatabaseService {

    private final RecipeService recipeService;
    private final CommentService commentService;
    private final CookUserService cookUserService;
    private final RatingService ratingService;

    @Autowired
    public MainDatabaseServiceImpl(RecipeService recipeService, CommentService commentService, CookUserService cookUserService, RatingService ratingService) {
        this.recipeService = recipeService;
        this.commentService = commentService;
        this.cookUserService = cookUserService;
        this.ratingService = ratingService;
    }

    //RecipeService
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }
    public Recipe createNewRecipe(Recipe recipe){
        return recipeService.createNewRecipe(recipe);
    }
    public Recipe getRecipeInfo(Integer id) {
        return recipeService.getRecipeInfo(id);
    }
    public List<Recipe> getAllRecipesOfUser(Integer id){
        return recipeService.getAllRecipesOfUser(id);
    }

    //CommentService
    public List<Comment> getRecipeComments(Integer id){
        return commentService.getRecipeComments(id);
    }
    public Comment updateComment(Comment comment){
        return commentService.updateComment(comment);
    }
    public List<Comment> getAllComments(){
        return commentService.getAllComments();
    }
    public Comment createNewComment(Comment comment){
        return commentService.addNewCommment(comment);
    }

    //CookUserService
    public List<CookUser> getAllUsers(){
        return cookUserService.getAllUsers();
    }

    //RatingService
    public Rating rateRecipe(Rating rating){
        return ratingService.addRating(rating);
    }

}

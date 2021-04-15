package com.just_cook.server.rest;


import com.just_cook.server.model.Comment;
import com.just_cook.server.model.CookUser;
import com.just_cook.server.model.Rating;
import com.just_cook.server.model.Recipe;
import com.just_cook.server.service.MainDatabaseServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private MainDatabaseServiceImpl databaseService;

    @Autowired
    public ApiController(MainDatabaseServiceImpl databaseService) {
        this.databaseService = databaseService;
    }

    @CrossOrigin
    @GetMapping("/allrecipes")
    public ResponseEntity<List<Recipe>> getAllPublishedRecipes(){
        List<Recipe> recipes = databaseService.getAllRecipes();
        return ResponseEntity.status(200).body(recipes);
    }

    @CrossOrigin
    @GetMapping("/allusers")
    public ResponseEntity <List<CookUser>> getAllUsers(){
        List<CookUser> users = databaseService.getAllUsers();
        return ResponseEntity.status(200).body(users);
    }

    @CrossOrigin
    @GetMapping("/userrecipes/{uid}")
    public ResponseEntity <?> getAllRecipesOfUser(@PathVariable("uid") Integer uid){
        List<Recipe> recipes = databaseService.getAllRecipesOfUser(uid);
        if(recipes.size()<1){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(recipes);
    }

    @CrossOrigin
    @GetMapping("/recipe/{id}")
    public ResponseEntity<Recipe> getRecipeInfo(@PathVariable("id") Integer id){
        Recipe recipe = databaseService.getRecipeInfo(id);
        if(recipe.getRecipeId() == null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(recipe);
    }

    @CrossOrigin
    @GetMapping("/recipecomments/{id}")
    public ResponseEntity <List<Comment>> getRecipeComments(@PathVariable("id") Integer id){
        List<Comment> comments = databaseService.getRecipeComments(id);
        return ResponseEntity.status(200).body(comments);
    }

    @CrossOrigin
    @GetMapping("/comments")
    public ResponseEntity <List<Comment>> getAllComments(){
        List<Comment> comments = databaseService.getAllComments();
        return ResponseEntity.status(200).body(comments);
    }

    @CrossOrigin
    @PutMapping("/updatecomment")
    public ResponseEntity <Comment> updateComment(@RequestBody Comment comment){
        Comment changedComment = databaseService.updateComment(comment);
        if(changedComment.getCommentID() == null){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(changedComment);
    }

    @CrossOrigin
    @PostMapping("/addRecipe")
    public ResponseEntity<Recipe> createNewRecipe(@RequestBody Recipe recipe){
        Recipe createdRecipe = databaseService.createNewRecipe(recipe);
        if(createdRecipe.getRecipeId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdRecipe,HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping("/addComment")
    public ResponseEntity<Comment> createNewComment(@RequestBody Comment comment){
        Comment createdComment = databaseService.createNewComment(comment);
        if(createdComment.getCommentID() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdComment,HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping("/addRating")
    public ResponseEntity<Rating> createNewRating(@RequestBody Rating rating){
        Rating addedRating = databaseService.rateRecipe(rating);
        if(addedRating.getUserId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(addedRating,HttpStatus.CREATED);
    }

}

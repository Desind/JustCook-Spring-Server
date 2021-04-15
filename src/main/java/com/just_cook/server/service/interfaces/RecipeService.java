package com.just_cook.server.service.interfaces;

import com.just_cook.server.model.Recipe;

import java.util.List;

public interface RecipeService extends DatabaseService{
    List<Recipe> getAllRecipes();
    Recipe createNewRecipe(Recipe recipe);
    List<Recipe> getAllRecipesOfUser(Integer id);
    Recipe getRecipeInfo(Integer id);
}

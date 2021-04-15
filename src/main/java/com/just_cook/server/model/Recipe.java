package com.just_cook.server.model;

public class Recipe {
    Integer recipeId;
    Integer userId;
    String name;
    String ingredients;
    String recipe;
    String status;

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getStatus() {
        return status;
    }

    public Recipe() {

    }

    public Recipe(Integer recipeId, Integer userId, String name, String ingredients, String recipe, String status) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.name = name;
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.status = status;
    }
    public Recipe(Integer userId, String name, String ingredients, String recipe, String status) {
        this.recipeId = null;
        this.userId = userId;
        this.name = name;
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "recipeID=" + recipeId +
                ", userID=" + userId +
                ", name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", recipe='" + recipe + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

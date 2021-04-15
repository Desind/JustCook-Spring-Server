package com.just_cook.server.model;

public class Rating {
    Integer userId;
    Integer recipeId;
    Integer rateValue;

    public Rating() {
        this.userId = null;
        this.recipeId = null;
        this.rateValue = null;
    }

    public Rating(Integer userId, Integer recipeId, Integer rateValue) {
        this.userId = userId;
        this.recipeId = recipeId;
        this.rateValue = rateValue;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getRateValue() {
        return rateValue;
    }

    public void setRateValue(Integer rateValue) {
        this.rateValue = rateValue;
    }
}

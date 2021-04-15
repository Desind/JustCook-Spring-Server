package com.just_cook.server.service.interfaces;

import com.just_cook.server.model.Rating;

public interface RatingService extends DatabaseService{
    Rating addRating(Rating rating);
}

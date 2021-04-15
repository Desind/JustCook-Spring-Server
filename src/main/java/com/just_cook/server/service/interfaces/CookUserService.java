package com.just_cook.server.service.interfaces;

import com.just_cook.server.model.CookUser;

import java.util.List;

public interface CookUserService extends DatabaseService{
    List<CookUser> getAllUsers();
}

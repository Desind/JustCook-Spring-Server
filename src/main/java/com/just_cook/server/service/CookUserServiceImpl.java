package com.just_cook.server.service;

import com.just_cook.server.model.CookUser;
import com.just_cook.server.service.interfaces.CookUserService;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CookUserServiceImpl implements CookUserService {

    @Override
    public List<CookUser> getAllUsers() {
        List<CookUser> data = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        try{
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            statement = connection.createStatement();
            result = statement.executeQuery("select user_id,username,email,registration_date from just_cook.cook_user order by registration_date");

            while(result.next()){
                CookUser user = new CookUser(result.getInt(1),result.getString(2),result.getString(3),result.getString(4));
                data.add(user);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(result != null && !result.isClosed()){
                    result.close();
                }
                if(statement != null && !statement.isClosed()){
                    statement.close();
                }
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            }catch(Exception e){
                System.out.println("Error. Closing rs & stmt & connection. Exception: " + e);
            }
        }
        return data;
    }
}

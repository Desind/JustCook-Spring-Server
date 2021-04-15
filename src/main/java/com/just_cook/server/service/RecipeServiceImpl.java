package com.just_cook.server.service;

import com.just_cook.server.model.Recipe;
import com.just_cook.server.service.interfaces.RecipeService;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RecipeServiceImpl implements RecipeService {
    @Override
    public List<Recipe> getAllRecipes() {
        List<Recipe> data = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        try{
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            statement = connection.createStatement();
            result = statement.executeQuery("select * from just_cook.recipe where recipe.status ='Published'");

            while(result.next()){
                Recipe recipe = new Recipe(result.getInt(1),result.getInt(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6));
                data.add(recipe);
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

    @Override
    public Recipe createNewRecipe(Recipe recipe) {
        Recipe createdRecipe = new Recipe();
        Connection connection = null;

        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            // Ustawienie trybu AUTOCOMMIT na tryb pełnej kontroli zatwierdzania oraz wycofywania transakcji.
            connection.setAutoCommit(false);

            PreparedStatement pstmt;
            ResultSet rs = null;
            String sql = "INSERT INTO just_cook.recipe (user_id,name,ingredients,recipe,status) VALUES (?,?,?,?,?) RETURNING *";

            pstmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pstmt.clearParameters();
            pstmt.setInt(1, recipe.getUserId());
            pstmt.setString(2, recipe.getName());
            pstmt.setString(3, recipe.getIngredients());
            pstmt.setString(4, recipe.getRecipe());
            pstmt.setString(5,"Published");

            try {
                rs = pstmt.executeQuery();
                rs.beforeFirst();
                if (rs.next()) {
                    createdRecipe.setRecipeId(rs.getInt(1));
                    createdRecipe.setUserId(rs.getInt(2));
                    createdRecipe.setName(rs.getString(3));
                    createdRecipe.setIngredients(rs.getString(4));
                    createdRecipe.setRecipe(rs.getString(5));
                    createdRecipe.setStatus(rs.getString(6));
                } else {
                    System.err.println("Information. No records were created.");
                }

            } catch (Exception e) {
                TimeUnit.SECONDS.sleep(3);
                connection.rollback();
                System.err.println("Error. Insert failed. Exception: " + e);
            }


        } catch (SQLException e) {
            System.err.println("Error SQL. Exception: " + e);
        } catch (Exception e) {
            System.err.println("Error. Exception: " + e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (Exception e) {
                System.err.println("Error. Setting AutoCommit failed. Exception: " + e);
            }
        }

        return createdRecipe;
    }

    @Override
    public List<Recipe> getAllRecipesOfUser(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Recipe> data = new ArrayList<>();
        try{
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            String query = "select * from just_cook.recipe where user_id = ?";
            preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.FETCH_FORWARD);
            preparedStatement.clearParameters();
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.beforeFirst();

            while(resultSet.next()){
                Recipe r = new Recipe();
                r.setRecipeId(resultSet.getInt(1));
                r.setUserId(resultSet.getInt(2));
                r.setName(resultSet.getString(3));
                r.setIngredients(resultSet.getString(4));
                r.setRecipe(resultSet.getString(5));
                r.setStatus(resultSet.getString(6));
                data.add(r);
            }
        }catch(SQLException e){
            System.err.println("Error SQL. Exception: " + e);
        }catch(Exception e){
            System.err.println("Error. Exception: " + e);
        }finally {
            try{
                if(resultSet != null && !resultSet.isClosed()){
                    resultSet.close();
                }
                if(preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
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

    @Override
    public Recipe getRecipeInfo(Integer id) {
        Recipe recipeInfo = new Recipe();
        Connection connection = null;

        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            // Ustawienie trybu AUTOCOMMIT na tryb pełnej kontroli zatwierdzania oraz wycofywania transakcji.
            connection.setAutoCommit(false);

            PreparedStatement pstmt;
            ResultSet rs;
            String sql = "select * from just_cook.recipe where recipe_id = ?";

            pstmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pstmt.clearParameters();
            pstmt.setInt(1, id);

            try {
                rs = pstmt.executeQuery();
                rs.beforeFirst();
                if (rs.next()) {
                    recipeInfo.setRecipeId(rs.getInt(1));
                    recipeInfo.setUserId(rs.getInt(2));
                    recipeInfo.setName(rs.getString(3));
                    recipeInfo.setIngredients(rs.getString(4));
                    recipeInfo.setRecipe(rs.getString(5));
                    recipeInfo.setStatus(rs.getString(6));
                } else {
                    System.err.println("Information. No records were created.");
                }

            } catch (Exception e) {
                TimeUnit.SECONDS.sleep(3);
                connection.rollback();
                System.err.println("Error. Insert failed. Exception: " + e);
            }


        } catch (SQLException e) {
            System.err.println("Error SQL. Exception: " + e);
        } catch (Exception e) {
            System.err.println("Error. Exception: " + e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (Exception e) {
                System.err.println("Error. Setting AutoCommit failed. Exception: " + e);
            }
        }

        return recipeInfo;
    }
}

package com.just_cook.server.service;

import com.just_cook.server.model.Rating;
import com.just_cook.server.service.interfaces.RatingService;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.concurrent.TimeUnit;

@Component
public class RatingServiceImpl implements RatingService {
    @Override
    public Rating addRating(Rating rating) {
        Rating createdRating = new Rating();
        Connection connection = null;

        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            PreparedStatement pstmt;
            ResultSet rs = null;
            String sql = "insert into just_cook.rating (user_id,recipe_id,rate_value) values (?,?,?) returning *;";

            pstmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pstmt.clearParameters();
            pstmt.setInt(1, rating.getUserId());
            pstmt.setInt(2, rating.getRecipeId());
            pstmt.setInt(3, rating.getRateValue());

            try {
                rs = pstmt.executeQuery();
                rs.beforeFirst();
                if (rs.next()) {
                    createdRating.setUserId(rs.getInt(1));
                    createdRating.setRecipeId(rs.getInt(2));
                    createdRating.setRateValue(rs.getInt(3));

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

        return createdRating;
    }
}

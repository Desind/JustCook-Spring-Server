package com.just_cook.server.service;

import com.just_cook.server.model.Comment;
import com.just_cook.server.service.interfaces.CommentService;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CommentServiceImpl implements CommentService {
    @Override
    public List<Comment> getRecipeComments(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Comment> data = new ArrayList<>();
        try{
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            String query = "select * from just_cook.comment where recipe_id = ? order by comment_id";
            preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.FETCH_FORWARD);
            preparedStatement.clearParameters();
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            resultSet.beforeFirst();

            while(resultSet.next()){
                Comment comment = new Comment(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3),resultSet.getString(4));
                data.add(comment);
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
    public Comment updateComment(Comment comment) {

        Comment updatedComment = new Comment();
        Connection connection = null;

        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            // Ustawienie trybu AUTOCOMMIT na tryb pełnej kontroli zatwierdzania oraz wycofywania transakcji.
            connection.setAutoCommit(false);

            PreparedStatement pstmt;
            ResultSet rs = null;
            String sql = "UPDATE just_cook.comment SET comment = ? WHERE comment_id = ? RETURNING *";

            pstmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pstmt.clearParameters();
            pstmt.setString(1, comment.getComment());
            pstmt.setInt(2, comment.getCommentID());

            try {
                rs = pstmt.executeQuery();
                rs.beforeFirst();
                if (rs.next()) {
                    updatedComment.setComment(comment.getComment());
                    updatedComment.setCommentId(comment.getCommentID());
                    updatedComment.setUserId(comment.getUserID());
                    updatedComment.setRecipeId(comment.getRecipeId());
                    if (comment.getCommentID() == null || comment.getComment() == null ||
                            comment.getRecipeId() == null || comment.getUserID() == null) {
                        System.out.println("There were an empty fields. " + updatedComment.toString());
                        TimeUnit.SECONDS.sleep(15);
                        connection.rollback();
                    } else {
                        TimeUnit.SECONDS.sleep(15);
                        connection.commit();
                        System.out.println("Information. Updated comment = " + updatedComment.toString());
                    }
                } else {
                    System.err.println("Information. No records were updated.");
                }
            } catch (Exception e) {
                TimeUnit.SECONDS.sleep(15);
                connection.rollback();
                System.err.println("Error. Update failed. Exception: " + e);
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    pstmt.close();
                } catch (Exception e) {
                    System.err.println("Error. Closing rs & pstmt. Exception: " + e);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error SQL. Exception: " + e);
        } catch (Exception e) {
            System.err.println("Error. Exception: " + e);
        } finally {
            try {
                if (connection != null) {
                    // Bardzo ważne jest, aby po zakończeniu transakcji ustawić z powrotem tryb AUTOCOMMIT = FALSE;.
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (Exception e) {
                System.err.println("Error. Setting AutoCommit failed. Exception: " + e);
            }
        }
        return updatedComment;
    }

    @Override
    public List<Comment> getAllComments() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Comment> commentsList = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            String sql = "SELECT * FROM just_cook.comment order by comment_id";
            stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);
            stmt.setFetchDirection(ResultSet.FETCH_FORWARD);
            stmt.setFetchSize(100);

            long startTime = System.currentTimeMillis();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Comment comment = new Comment(rs.getInt("comment_id"), rs.getInt("user_id"), rs.getInt("recipe_id"), rs.getString("comment"));
                commentsList.add(comment);
            }
            connection.commit();
            long endTime = System.currentTimeMillis();

        } catch (SQLException e) {
            System.err.println("Error SQL. Exception: " + e);
        } catch (Exception e) {
            System.err.println("Error. Exception: " + e);
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                if (stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (Exception e) {
                System.out.println("Error. Closing rs & stmt & connection. Exception: " + e);
            }
        }
        return commentsList;
    }

    @Override
    public Comment addNewCommment(Comment comment) {
        Comment createdComment = new Comment();
        Connection connection = null;

        try {
            Class.forName(JDBC_DRIVER);

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            PreparedStatement pstmt;
            ResultSet rs = null;
            String sql = "INSERT INTO just_cook.comment (user_id,recipe_id,comment) VALUES (?,?,?) RETURNING *";

            pstmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pstmt.clearParameters();
            pstmt.setInt(1, comment.getUserID());
            pstmt.setInt(2, comment.getRecipeId());
            pstmt.setString(3, comment.getComment());

            try {
                rs = pstmt.executeQuery();
                rs.beforeFirst();
                if (rs.next()) {
                    createdComment.setCommentId(rs.getInt(1));
                    createdComment.setUserId(rs.getInt(2));
                    createdComment.setRecipeId(rs.getInt(3));
                    createdComment.setComment(rs.getString(4));
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

        return createdComment;
    }
}

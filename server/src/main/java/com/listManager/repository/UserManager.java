package com.listManager.repository;

import com.listManager.util.PropertiesUtil;
import com.listManager.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserManager {
    private static final Logger log = LoggerFactory.getLogger(UserManager.class);
    private final DataSource dataSource;
    public UserManager() {
        String name = PropertiesUtil.get("db.username");
        String password = PropertiesUtil.get("db.password");
        String url = PropertiesUtil.get("db.url");
        dataSource = new DriverManagerDataSource(url, name, password);
    }

    public void registerUser(User user) {
        String sqlInsert = "INSERT INTO users(username, password) VALUES(?,?) RETURNING id";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert)){
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int userId = resultSet.getInt("id");
            user.setUserID(userId);
        } catch (SQLException e) {
            log.error("User has not been created", e);
            throw new RuntimeException(e);
        }
    }


    public void deleteUser(int userID) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement=conn.prepareStatement(sql)) {
            preparedStatement.setInt(1,userID);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("User{} hasn't been deleted", userID, e);
            throw new RuntimeException(e);
        }
    }
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password FROM users WHERE username=?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) {
                return null;
            }
            return new User(
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getInt("id")
            );
        } catch (SQLException e) {
            log.error("User has not been found", e);
            throw new RuntimeException(e);
        }
    }
}
package com.listManager.repository;

import com.listManager.model.User;
import com.listManager.model.UserList;
import com.listManager.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ListRepository {
    private static final Logger log = LoggerFactory.getLogger(ListRepository.class);
    private final DataSource dataSource;

    public ListRepository() {
        String name = PropertiesUtil.get("db.username");
        String password = PropertiesUtil.get("db.password");
        String url = PropertiesUtil.get("db.url");
        dataSource = new DriverManagerDataSource(url, name, password);
    }

    public List<String> getUserListRows(int listID) {
        String sql = "SELECT row_data FROM list_data WHERE list_id=?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setInt(1, listID);

            List<String> rows = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String rowData = resultSet.getString("row_data");
                rows.add(rowData);
            }
            return rows;
        } catch (SQLException e) {
            log.error("List has not been found", e);
            throw new RuntimeException(e);
        }
    }

    public String getUserListName(int listID) {
        String sql = "SELECT list_name FROM user_list WHERE list_id=?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setInt(1, listID);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("list_name");
        } catch (SQLException e) {
            log.error("List name has not been found", e);
            throw new RuntimeException(e);
        }
    }

    public int createList(int userID, String listName) {
        String sql = "INSERT INTO user_list(user_id, list_name) " +
                "VALUES(?, ?)" +
                "RETURNING list_id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, listName);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt("list_id");
        } catch (SQLException e) {
            log.error("List hasn't been saved", e);
            throw new RuntimeException(e);
        }
    }
}


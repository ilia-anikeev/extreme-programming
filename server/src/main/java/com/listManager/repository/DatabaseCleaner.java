package com.listManager.repository;

import com.listManager.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseCleaner {
    private static final Logger log = LoggerFactory.getLogger(DatabaseCleaner.class);
    private final DataSource dataSource;

    public DatabaseCleaner() {
        String name = PropertiesUtil.get("db.username");
        String password = PropertiesUtil.get("db.password");
        String url = PropertiesUtil.get("db.url");
        dataSource = new DriverManagerDataSource(url, name, password);
    }

    public void deleteDB() {
        try (Connection conn = dataSource.getConnection()){
            List<String> tables = List.of("users");
            for (String tableName: tables) {
                String sql = "DROP TABLE " + tableName;
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.execute();
            }

        } catch (SQLException e) {
            log.error("Delete database error", e);
            throw new RuntimeException(e);
        }
    }
}

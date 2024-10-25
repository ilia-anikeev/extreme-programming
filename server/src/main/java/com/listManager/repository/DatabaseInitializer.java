package com.listManager.repository;

import com.listManager.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitializer {
    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);
    private final DataSource dataSource;

    public DatabaseInitializer() {
        String name = PropertiesUtil.get("db.username");
        String password = PropertiesUtil.get("db.password");
        String url = PropertiesUtil.get("db.url");
        dataSource = new DriverManagerDataSource(url, name, password);
    }
    public void initUserTable(Connection conn ){
        String sql = "CREATE TABLE users(id SERIAL PRIMARY KEY, email TEXT, username TEXT, password TEXT)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Init users table error", e);
            throw new RuntimeException(e);
        }
    }
    public void initDB() {
        try (Connection conn = dataSource.getConnection()) {

            initUserTable(conn);

            log.info("Database initialization was successful");
        } catch (SQLException e) {
            log.error("Database haven't been initialized", e);
            throw new RuntimeException(e);
        }
    }
}

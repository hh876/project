package com.yjj.campustradeplatform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            addColumnIfNotExists(connection, "user", "role", "VARCHAR(20) DEFAULT 'both'");
            addColumnIfNotExists(connection, "goods", "category_id", "BIGINT DEFAULT 0");
            addColumnIfNotExists(connection, "goods", "image_url", "VARCHAR(500)");
        }
    }

    private void addColumnIfNotExists(Connection connection, String tableName, String columnName, String columnDefinition) throws Exception {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(null, null, tableName, columnName);
        
        if (!columns.next()) {
            try (Statement statement = connection.createStatement()) {
                String sql = String.format("ALTER TABLE `%s` ADD COLUMN `%s` %s", tableName, columnName, columnDefinition);
                statement.execute(sql);
                System.out.println("Added column " + columnName + " to table " + tableName);
            }
        }
        columns.close();
    }
}

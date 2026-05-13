package com.anastasiia.repeto.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:repeto.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void migrate() {
        try(Connection c = getConnection(); Statement st = c.createStatement()) {
            st.execute("""
                CREATE TABLE IF NOT EXISTS questions(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    tag TEXT,
                    text TEXT NOT NULL,
                    type TEXT NOT NULL
                );
            """);
            st.execute("""
                CREATE TABLE IF NOT EXISTS answers(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    question_id INTEGER NOT NULL,
                    answer TEXT NOT NULL,
                    is_correct BOOLEAN NOT NULL,
                    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
                );
            """);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
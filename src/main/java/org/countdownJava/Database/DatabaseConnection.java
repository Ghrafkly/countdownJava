package org.countdownJava.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection connection;


    public Connection connect() {
        Connection conn = null;
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "postgres";
        String password = "password";

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        db.connect();
    }
}


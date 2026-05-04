package com.pos.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // jdbc:h2:~/mi_tienda significa que guarda el archivo en tu carpeta de usuario
    private static final String URL = "jdbc:h2:~/mi_tienda;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

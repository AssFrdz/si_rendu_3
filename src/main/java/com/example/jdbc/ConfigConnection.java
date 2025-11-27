package com.example.jdbc;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public final class ConfigConnection {

    public static Connection getConnection() throws Exception {
        Properties p = new Properties();

        // Load database.properties from classpath
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                // Fallback
                InputStream fallback = ConfigConnection.class.getResourceAsStream("/database.properties");
                if (fallback == null) {
                    URL root = ConfigConnection.class.getResource("/");
                    throw new IllegalStateException(
                        "database.properties not found on classpath.\n" +
                        "Checked via context ClassLoader. Classpath root (via class): " + root
                    );
                }
                p.load(fallback);
            } else {
                p.load(input);
            }
        }

        String driver = p.getProperty("driver");
        String url    = p.getProperty("url");
        String user   = p.getProperty("utilisateur");
        String pass   = p.getProperty("mdp");

        if (driver != null && !driver.isBlank()) {
            Class.forName(driver); // charge le driver JDBC
        }

        return DriverManager.getConnection(url, user, pass);
    }

    private ConfigConnection() {} // private constructor to prevent instantiation
}

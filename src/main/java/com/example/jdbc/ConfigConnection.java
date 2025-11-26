package main.java.com.example.jdbc;

import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

public final class ConfigConnection {

    public static Connection getConnection() throws Exception {
        // Load from classpath root: resources/database.properties
        String res = "database.properties"; // no leading slash with ClassLoader API
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(res);
        if (in == null) {
            // Helpful diagnostics
            URL root = ConfigConnection.class.getResource("/");
            throw new IllegalStateException(
                "database.properties not found on classpath.\n" +
                "Checked via context ClassLoader. Classpath root (via class): " + root
            );
        }

        Properties p = new Properties();
        try (in) { p.load(in); }

        String driver = p.getProperty("driver");
        String url    = p.getProperty("url");
        String user   = p.getProperty("utilisateur");
        String pass   = p.getProperty("mdp");

        if (driver != null && !driver.isBlank()) {
            Class.forName(driver); // optional in Java 8+ but fine
        }
        return DriverManager.getConnection(url, user, pass);
    }

    private ConfigConnection() {}
}

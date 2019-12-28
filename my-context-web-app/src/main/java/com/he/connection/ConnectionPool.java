package com.he.connection;

import com.he.context.Component;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionPool implements Closeable, Component {

    private Connection connection;

    public Connection getConnection() {
        try {
            if (Objects.isNull(connection) || connection.isClosed())
                instantiate();
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private void instantiate() {
        try {
            String dbUrl      = "jdbc:mysql://localhost:3306/javalab_shop?useUnicode=true&serverTimezone=UTC";
            String dbUsername = "root";
            String dbPassword = "vjhrjdm[fym";

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getName() {
        return "connection";
    }
}

package springbook.user.dao;

import sun.java2d.pipe.SpanShapeRenderer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {
    public DConnectionMaker() {}

    public Connection makeConnection() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook", "RyuJaesang", "wotkd1112");

        return c;
    }

}

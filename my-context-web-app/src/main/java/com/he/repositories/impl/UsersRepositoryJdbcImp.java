package com.he.repositories.impl;

import com.he.context.Component;
import com.he.models.User;
import com.he.repositories.RowMapper;
import com.he.repositories.UsersRepository;
import com.he.connection.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsersRepositoryJdbcImp implements UsersRepository, Component {

    private ConnectionPool connectionPool;

    private RowMapper<User> rowMapper = resultSet -> new User.Builder()
            .id(resultSet.getInt("id"))
            .login(resultSet.getString("login"))
            .password(resultSet.getString("password"))
            .role(resultSet.getString("role"))
            .build();

    //language=MySQL
    private final String ADD_USER = "" +
            "INSERT INTO javalab_shop.user(login, password, role) VALUES(?, ?, ?)";

    //language=MySQL
    private final String GET_USER = "" +
            "SELECT * FROM javalab_shop.product WHERE id_product = ?";

    //language=MySQL
    private final String GET_LAST_ID = "" +
            "SELECT max(id_user) from javalab_shop.user";

    //language=MySQL
    private final String GET_USER_BY_LOGIN = "" +
            "SELECT * FROM javalab_shop.user where username = ?";

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            PreparedStatement stmt = connectionPool.getConnection().prepareStatement(GET_USER_BY_LOGIN);
            stmt.setString(1, login);
            ResultSet set = stmt.executeQuery();
            if (set.next())
                return Optional.of(rowMapper.rowMap(set));
            else return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findOne(Integer userId) {
        try {
            PreparedStatement stmt = connectionPool.getConnection().prepareStatement(GET_USER);
            stmt.setLong(1, userId);
            ResultSet set = stmt.executeQuery();
            set.next();
            return Optional.of(rowMapper.rowMap(set));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public int save(User user) {
        try {
            PreparedStatement stmt = connectionPool.getConnection().prepareStatement(ADD_USER);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            if (stmt.executeUpdate() > 0) {
                PreparedStatement stmt2 = connectionPool.getConnection().prepareStatement(GET_LAST_ID);
                ResultSet set = stmt2.executeQuery();
                set.next();
                return Integer.parseInt(set.getString("id"));
            }
            throw new IllegalStateException("Error, can't save");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getName() {
        return "usersRepository";
    }

    @Override
    public String toString() {
        return "UsersRepositoryFakeImpl{" +
                "connection=" + connectionPool +
                '}';
    }
}

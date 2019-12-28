package com.he.repositories.impl;

import com.he.connection.ConnectionPool;
import com.he.context.Component;
import com.he.models.Product;
import com.he.repositories.ProductRepository;
import com.he.repositories.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryJdbcImp implements ProductRepository, Component {

    private ConnectionPool connectionPool;

    private RowMapper<Product> productMapper = resultSet -> new Product.Builder()
            .id(resultSet.getInt("id"))
            .name(resultSet.getString("name"))
            .build();

    //language=MySQL
    private final String SQL_SELECT_ALL_PRODUCTS = "" +
            "SELECT * FROM javalab_shop.product";

    //language=MySQL
    private final String SQL_GET_USER_PRODUCTS = "" +
            "SELECT javalab_shop.product.id_product, javalab_shop.product.name FROM javalab_shop.shop INNER JOIN javalab_shop.product ON javalab_shop.product.id_product WHERE javalab_shop.shop.id_user = ?";

    //language=MySQL
    private final String SQL_BUY_PRODUCT = "" +
            "INSERT INTO javalab_shop.shop(id_user, id_product) VALUES(?, ?)";

    //language=MySQL
    private final String SQL_SELECT_PRODUCT_BY_ID = "" +
            "SELECT * FROM javalab_shop.product WHERE id_product = ?";

    //language=MySQL
    private final String SQL_ADD_PRODUCT_BY_NAME = "" +
            "INSERT INTO javalab_shop.product(name) VALUES(?)";

    //language=MySQL
    private final String SQL_GET_LAST_PRODUCT_ID = "" +
            "SELECT max(id_product) FROM javalab_shop.product";


    @Override
    public List<Product> findAll() {
        try {
            PreparedStatement stmt = connectionPool.getConnection().prepareStatement(SQL_SELECT_ALL_PRODUCTS);
            ResultSet resultSet = stmt.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(productMapper.rowMap(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Product> getUserProducts(Integer userId) {
        try {
            PreparedStatement stmt = connectionPool.getConnection().prepareStatement(SQL_GET_USER_PRODUCTS);
            stmt.setInt(1, userId);
            ResultSet resultSet = stmt.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(productMapper.rowMap(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean buy(Integer userId, Integer productId) {
        try {
            PreparedStatement stmt = connectionPool.getConnection().prepareStatement(SQL_BUY_PRODUCT);
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Product> findOne(Integer productId) {
        try {
            PreparedStatement stmt = connectionPool.getConnection().prepareStatement(SQL_SELECT_PRODUCT_BY_ID);
            stmt.setInt(1, productId);
            ResultSet set = stmt.executeQuery();
            if (set.next())
                return Optional.of(productMapper.rowMap(set));
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public int save(Product product) {
        try {
            PreparedStatement stmt = connectionPool.getConnection().prepareStatement(SQL_ADD_PRODUCT_BY_NAME);
            stmt.setString(1, product.getName());
            if (stmt.executeUpdate() > 0) {
                stmt.close();
                PreparedStatement stmt2 = connectionPool.getConnection().prepareStatement(SQL_GET_LAST_PRODUCT_ID);
                ResultSet set = stmt2.executeQuery();
                set.next();
                return Integer.parseInt(set.getString("id"));
            }
            throw new IllegalStateException("data base wasn't changed");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String toString() {
        return "ProductRepositoryJdbcImpl{" +
                "connectionPool=" + connectionPool +
                '}';
    }

    @Override
    public String getName() {
        return "productRepository";
    }
}

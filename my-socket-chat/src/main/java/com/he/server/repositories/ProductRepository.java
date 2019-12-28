package com.he.server.repositories;

import com.he.server.models.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAll();

    List<Product> getUserProducts(Integer userId);

    boolean buy(Integer userId, Integer productId);

}

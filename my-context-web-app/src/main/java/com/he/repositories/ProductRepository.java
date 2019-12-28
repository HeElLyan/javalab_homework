package com.he.repositories;

import com.he.models.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAll();

    List<Product> getUserProducts(Integer userId);

    boolean buy(Integer userId, Integer productId);

}

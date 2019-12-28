package com.he.server.services.impl;

import com.he.server.dto.ListDto;
import com.he.server.dto.ProductDto;
import com.he.server.models.Product;
import com.he.server.repositories.UsersRepository;
import com.he.context.Component;
import com.he.server.repositories.ProductRepository;
import com.he.server.services.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService, Component {
    private ProductRepository productRepository;
    private UsersRepository usersRepository;

    @Override
    public ListDto<ProductDto> getAllProducts() {
        return ListDto.fromList(productRepository.findAll(), ProductDto::from);
    }

    @Override
    public boolean buyProduct(Integer userId, Integer productId) {
        List<Product> productList = productRepository.getUserProducts(userId);
        for (Product product : productList)
            if (product.getId() == productId)
                throw new IllegalStateException("Already bought");
        return productRepository.buy(userId, productId);
    }

    @Override
    public ProductDto addProduct(String name) {
        Product product = new Product.Builder().name(name).build();
        int id = productRepository.save(product);
        System.out.println(id);
        product.setId(id);
        return ProductDto.from(product);
    }

    @Override
    public ListDto<ProductDto> getUserProducts(Integer userId) {
        return ListDto.fromList(productRepository.getUserProducts(userId), ProductDto::from);
    }

    @Override
    public String getName() {
        return "productService";
    }

    @Override
    public String toString() {
        return "ProductServiceImpl{" +
                "productRepository=" + productRepository +
                '}';
    }

}

package com.he.server.services;

import com.he.server.dto.ListDto;
import com.he.server.dto.ProductDto;

public interface ProductService {
    ListDto<ProductDto> getAllProducts();
    boolean buyProduct(Integer userId, Integer productId);
    ProductDto addProduct(String name);
    ListDto<ProductDto> getUserProducts(Integer userId);
}

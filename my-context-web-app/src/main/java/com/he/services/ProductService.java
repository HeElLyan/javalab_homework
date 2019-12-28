package com.he.services;

import com.he.dto.ListDto;
import com.he.dto.ProductDto;

public interface ProductService {
    ListDto<ProductDto> getAllProducts();
    boolean buyProduct(Integer userId, Integer productId);
    ProductDto addProduct(String name);
    ListDto<ProductDto> getUserProducts(Integer userId);
}

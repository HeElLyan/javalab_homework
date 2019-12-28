package com.he.dto;

import com.he.models.Product;

public class ProductDto implements Dto {
    private Integer   id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private ProductDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static class Builder {
        private Integer   id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ProductDto build() {
            return new ProductDto(id, name);
        }

    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "} ";
    }

    public static ProductDto from(Product product) {
        return new ProductDto(product.getId(), product.getName());
    }

}

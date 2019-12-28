package com.he.models;

public class Product {
    private Integer id;
    private String name;

    private Product(Builder builder) {
        this.id = builder.getId();
        this.name = builder.getName();
    }

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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Builder {
        private Integer   id;
        private String name;

        public Integer getId() {
            return id;
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

}

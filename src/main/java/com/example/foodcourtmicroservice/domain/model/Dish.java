package com.example.foodcourtmicroservice.domain.model;

public class Dish {
    private Long id;
    private String name;
    private Category idCategory;
    private String description;
    private String price;
    private Restaurant idRestaurant;
    private String urlImage;
    private Boolean active;

    public Dish() {
    }

    public Dish(Long id, String name, Category idCategory, String description, String price, Restaurant idRestaurant, String urlImage, Boolean active) {
        this.id = id;
        this.name = name;
        this.idCategory = idCategory;
        this.description = description;
        this.price = price;
        this.idRestaurant = idRestaurant;
        this.urlImage = urlImage;
        this.active = active;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public Category getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Category idCategory) {
        this.idCategory = idCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Restaurant getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Restaurant idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}

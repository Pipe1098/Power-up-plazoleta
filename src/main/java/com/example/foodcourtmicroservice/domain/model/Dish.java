package com.example.foodcourtmicroservice.domain.model;

public class Dish {
    private Long id;
    private String name;
    private Category idCategory;
    private Long idCategoryAux;
    private String description;
    private String price;
    private Restaurant idRestaurant;
    private Long idRestaurantAux;
    private String urlImage;
    private Boolean active;

    public Dish() {
    }

    public Dish(Long id, String name, Category idCategory, Long idCategoryAux, String description, String price, Restaurant idRestaurant, Long idRestaurantAux, String urlImage, Boolean active) {
        this.id = id;
        this.name = name;
        this.idCategory = idCategory;
        this.idCategoryAux = idCategoryAux;
        this.description = description;
        this.price = price;
        this.idRestaurant = idRestaurant;
        this.idRestaurantAux = idRestaurantAux;
        this.urlImage = urlImage;
        this.active = active;
    }

    public Long getIdCategoryAux() {
        return idCategoryAux;
    }

    public void setIdCategoryAux(Long idCategoryAux) {
        this.idCategoryAux = idCategoryAux;
    }

    public Long getIdRestaurantAux() {
        return idRestaurantAux;
    }

    public void setIdRestaurantAux(Long idRestaurantAux) {
        this.idRestaurantAux = idRestaurantAux;
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

    public String getDescripcion() {
        return description;
    }

    public void setDescripcion(String description) {
        this.description = description;
    }

    public String getPrecio() {
        return price;
    }

    public void setPrecio(String price) {
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

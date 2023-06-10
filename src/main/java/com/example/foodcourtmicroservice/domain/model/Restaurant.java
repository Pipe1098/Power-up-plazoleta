package com.example.foodcourtmicroservice.domain.model;

public class Restaurant {
    private Long id;
    private String name;
    private String adress;
    private String phone;
    private String urlLogotype;
    private String nit;
    private String idOwner;

    public Restaurant(Long id, String name, String adress, String phone, String urlLogotype, String nit, String idOwner) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.phone = phone;
        this.urlLogotype = urlLogotype;
        this.nit = nit;
        this.idOwner = idOwner;
    }

    public Restaurant() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrlLogotype() {
        return urlLogotype;
    }

    public void setUrlLogotype(String urlLogotype) {
        this.urlLogotype = urlLogotype;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }
}

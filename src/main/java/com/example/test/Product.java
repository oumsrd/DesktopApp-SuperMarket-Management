package com.example.test;

public class Product {
    private int productId;
    private String productName;
    private int productPrice;
    private int productQty;
    private int fourniseur;

    public Product(int productId, int fourniseur ,String productName, int productQty,int productPrice) {
        this.productId = productId;
        this.fourniseur= fourniseur;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQty = productQty;

    }

    public int getFourniseur() {
        return fourniseur;
    }

    public void setFourniseur(int fourniseur) {
        this.fourniseur = fourniseur;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }
}

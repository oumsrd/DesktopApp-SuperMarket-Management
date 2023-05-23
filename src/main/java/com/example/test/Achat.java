package com.example.test;
import java.util.Date;

public class Achat {
    Integer id_Achat ,produit ,quantite ,remise , prix ;
    Date date;

    public Integer getId_Achat() {
        return id_Achat;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public Integer getRemise() {
        return remise;
    }


    public Integer getPrix() {
        return prix;
    }

    public  Date getDate() {
        return date;
    }

    public Integer getProduit() {
        return produit;
    }

    public void setId_Achat(Integer id_vente) {
        this.id_Achat = id_Achat;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public void setRemise(Integer remise) {
        this.remise = remise;
    }


    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setProduit(Integer produit) {
        this.produit = produit;
    }

    public Achat(Integer id_Achat, Integer produit, Date date, Integer prix, Integer quantite, Integer remise){


        this.id_Achat=id_Achat;
        this.produit=produit;
        this.date=date;
        this.quantite=quantite;
        this.remise=remise;
        this.prix=prix;
    }
    public Achat(Integer id_Achat, Integer produit, Integer prix, Integer quantite, Integer remise){


        this.id_Achat=id_Achat;
        this.produit=produit;
        this.quantite=quantite;
        this.remise=remise;
        this.prix=prix;
    }




}
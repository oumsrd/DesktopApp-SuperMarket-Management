package com.example.test;

public class classfournisseur {
    String nom;
    String adresse;
    String ville;
    String code;
    String tel;

    public classfournisseur(String code, String nom, String adresse, String ville, String tel) {
        this.code = code;
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
        this.tel = tel;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCode() {
        return this.code;
    }

    public String getNom() {
        return this.nom;
    }
    public String getVille(){ return this.ville;  }
    public String getTel(){ return this.tel;  }
    public String getAdresse() {
        return this.adresse;
    }
}
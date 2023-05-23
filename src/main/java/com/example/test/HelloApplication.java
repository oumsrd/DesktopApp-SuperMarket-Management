package com.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.io.IOException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("1");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Connection con=null;
        Statement stmt=null;
        String DatabaseName="database1";
        try {
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root","sapnay");
            stmt = con.createStatement();
            //stmt.executeUpdate("DROP DATABASE database1 if exist");
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS database1");
            stmt.executeUpdate("USE database1");
            stmt.executeUpdate("create table IF NOT EXISTS user_account( firstname varchar(100) primary key ,\r\n" + "lastname varchar(100),\r\n" + "username varchar(100),\r\n"+"password varchar(100))");
            stmt.executeUpdate("create table IF NOT EXISTS vente(id_vente int(10) primary key,\r\n" + "produit int(10),\r\n" + "dateV date,\r\n" + "prixProd int(10),\r\n" + "quantite int(10),\r\n" + "remise int(10),\r\n" + "prixTotal int(10))");
            stmt.executeUpdate("create table IF NOT EXISTS Achat(id_achat int(10) primary key,\r\n" + "produit int(10),\r\n" + "dateV date,\r\n" + "prixProd int(10),\r\n" + "quantite int(10),\r\n" + "remise int(10),\r\n" + "prixTotal int(10))");
            stmt.executeUpdate("create table IF NOT EXISTS fournisseur(CodeFrnsr int(100) primary key,\r\n" + "NomFrnsr varchar(100),\r\n" + "AdresseFrnsr varchar(100),\r\n" + "VilleFrnsr varchar(100),\r\n" + "TelFrnsr varchar(100))");
            stmt.executeUpdate("create table IF NOT EXISTS products(prodid int(10) primary key ,\r\n" + "fornID int(10),\r\n" + "prodnom varchar(20),\r\n" + "qty int(10),\r\n" + "price int(10))");
            System.out.println("database is created from main");
            DATABASE bdb= new DATABASE();
            bdb.Connect();
            launch();
        }catch(Exception e){
            e.printStackTrace();}

    }
}
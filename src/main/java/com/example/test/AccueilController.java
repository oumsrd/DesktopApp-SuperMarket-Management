package com.example.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;




public class AccueilController {

    @FXML
    private AnchorPane fenetre1accueil;

    @FXML
    private AnchorPane fenetre2;

    @FXML
    private AnchorPane fenetre3;

    @FXML
    private Button idbuttonachat;

    @FXML
    private Button idbuttonfournisseur;

    @FXML
    private Button idbuttonproduit;

    @FXML
    private Button idbuttonretour;

    @FXML
    private Button idbuttonvente;

    @FXML
    private ImageView imgacceil;

    @FXML
    void Achatsbutton(ActionEvent event) throws IOException {

        Stage stage = (Stage) idbuttonvente.getScene().getWindow();
        FXMLLoader  fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Achat.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("les ventes");
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void Fournisseurbutton(ActionEvent event) throws IOException {


        Stage stage = (Stage) idbuttonvente.getScene().getWindow();
        FXMLLoader  fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fournisseur.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("les ventes");
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void Produitsbutton(ActionEvent event) throws IOException {

        Stage stage = (Stage) idbuttonvente.getScene().getWindow();
        FXMLLoader  fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("product.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("les ventes");
        stage.setScene(scene);
        stage.show();



    }

    @FXML
    void Retourbutton(ActionEvent event) throws IOException {


        Stage stage = (Stage) idbuttonvente.getScene().getWindow();
        FXMLLoader  fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("les ventes");
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    void Ventebutton(ActionEvent event) throws IOException {
        Stage stage = (Stage) idbuttonvente.getScene().getWindow();
        FXMLLoader  fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("vente.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("les ventes");
        stage.setScene(scene);
        stage.show();

    }

}

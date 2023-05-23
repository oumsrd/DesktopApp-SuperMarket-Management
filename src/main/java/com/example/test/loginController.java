package com.example.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


import javafx.scene.control.*;
import javafx.stage.Window;


public class loginController {
    @FXML
    private Button loginbutton;
    @FXML
    private Button inscrireButton;



    @FXML
    private  Label loginMessagelabel;

    @FXML
     private ImageView brandingImageView;

    @FXML
    private ImageView LoginImageView;
    @FXML
    private TextField usernametextfield;
    @FXML
    private PasswordField passwordtextfield;
/*
    @Override

    public void initialize (URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("@supermarkets.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);


        File loginFile = new File("@login.png");
        Image LoginImage = new Image(loginFile.toURI().toString());
        LoginImageView.setImage(LoginImage);
    }*/

@FXML

    public  void loginButtonOnAction(ActionEvent event) throws IOException {

    Window owner= loginbutton.getScene().getWindow();
   // String username = usernametextfield.getText();
    //String password = passwordtextfield.getText();
    if (usernametextfield.getText().isEmpty() ||passwordtextfield.getText().isEmpty()) {
        showAlert(Alert.AlertType.ERROR, owner, "ERROR!",
                " Please enter your username and password  ");
    }
    else {
        validateLogin();
    }



        /*loginMessagelabel.setText("try to connect again");*/

       /* if (usernametextfield.getText().isBlank() == false && passwordtextfield.getText().isBlank()== false){
            validateLogin();
        }
        else {
            loginMessagelabel.setText("please enter username and password ");
        }*/

    }


    public void inscrireButtonOnAction (ActionEvent event)  throws  Exception{
        Stage stage =(Stage) inscrireButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

    public  void  validateLogin(){
        DatabaseConnection connectNow =new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "SELECT  count(1)  FROM  user_account  WHERE username = '"+ usernametextfield.getText() + "' AND password ='" + passwordtextfield.getText() + "'";
        try {
            Statement statement= connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if(queryResult.getInt(1)== 1){
                    //loginMessagelabel.setText("Congrats");

                    Stage stage =(Stage) loginbutton.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueil.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();

                }
                else {
                    loginMessagelabel.setText("invalid login");
                    clearFields();
                }
            }

        }catch (Exception e) {
            Window owner= loginbutton.getScene().getWindow();
            e.printStackTrace();
            e.getCause();
           /* showAlert(Alert.AlertType.ERROR, owner, "ERROR!",
                    "Les informations ne sont pas valides");*/
            clearFields();
        }
    }
/*
    public  void  createAccountForm(){
    try{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

    }catch(Exception e){
        e.printStackTrace();
        e.getCause();
    }
    }*/
    static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    public void clearFields(){
        usernametextfield.clear();
        passwordtextfield.clear();

    }
}
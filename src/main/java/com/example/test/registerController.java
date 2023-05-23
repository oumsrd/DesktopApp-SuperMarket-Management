package com.example.test;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.Statement;
import java.sql.Connection;


public class registerController /*implements Initializable */{


    @FXML
    private Button closeButton;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private ImageView keyimageView;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private Button registerButton;

    @FXML
    private PasswordField setPasswordTextField;

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField confirmPassword;
    @FXML

    private Label registrationLabelMessage;
    @FXML
    private Label confirmationMessageLabel;

    /*public void initialize (URL url, ResourceBundle resourceBundle){
        File keyFile = new File("@key.png");
        Image keyImage = new Image(keyFile.toURI().toString());
        keyimageView.setImage(keyImage);

    }*/

    public void  closeButtonOnAction(ActionEvent event) throws  Exception{
        /*Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();*/

        Stage stage = (Stage) closeButton.getScene().getWindow();
        FXMLLoader fxmlLoader =new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();



    }

    public void  registerButtonOnAction(ActionEvent event){
        //registrationLabelMessage.setText("User has been registred succefully");
        Window owner= registerButton.getScene().getWindow();
        String username = usernameTextField.getText();
        String password = setPasswordTextField.getText();
        if (username.isEmpty() ||password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "ERROR!",
                    "Please enter your username and password ");
            return;
        } else if (setPasswordTextField.getText().equals(confirmPassword.getText())) {
            registerUser();
            confirmationMessageLabel.setText("");

        }
       else {
            confirmationMessageLabel.setText("password does not march");
        }
    }

    public  void registerUser(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstnameTextField.getText();
        String lastname =lastnameTextField.getText();
        String username =usernameTextField.getText();
        String password=setPasswordTextField.getText();
        String insertFields ="insert into user_account(firstname, lastname,username,password) values('";

        String insertValues =firstname +"','"+lastname +"','"+ username  +"','"+ password +"')";
         String insertToregister = insertFields + insertValues;

         try {
         Statement statement = connectDB.createStatement();
         statement.executeUpdate(insertToregister);
             registrationLabelMessage.setText("User has been registred succefully");
         } catch (Exception e){
             e.printStackTrace();
             e.getCause();

         }

    }
    static  void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }



}

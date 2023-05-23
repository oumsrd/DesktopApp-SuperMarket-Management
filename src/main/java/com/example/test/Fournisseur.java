package com.example.test;
import  com.example.test.DATABASE;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import com.example.test.classfournisseur;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.sql.Connection;
public class Fournisseur implements Initializable {

    @FXML
    private TableColumn<classfournisseur, String> adresse_col;

    @FXML
    private TextField adressetextfield;

    @FXML
    private Button ajouterbutton;

    @FXML
    private TextField citytextfield;

    @FXML
    private Button clearButton;

    @FXML
    private Button Retourbutton;
    @FXML
    private TableColumn<classfournisseur, Integer> id_col;

    @FXML
    private TextField idtextfield;

    @FXML
    private Button modifierbutton;

    @FXML
    private TextField nametextfield;

    @FXML
    private TableColumn<classfournisseur,String> nom_col;

    @FXML
    private AnchorPane pane_fornisseurs;

    @FXML
    private TextField phonetextfield;

    @FXML
    private Button supprimerbutton;
    @FXML
    private TextField filterField;

    @FXML
    private TableView<classfournisseur> tablefournisseur;

    @FXML
    private TableColumn<classfournisseur, Integer> tel_col;
    @FXML
    private TableColumn<classfournisseur, String> ville_col;

    private Connection con = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showsFournisseur();
        this.RechercheFilter();
    }
    @FXML
    public void onTableItemSelect() {
        classfournisseur fr = (classfournisseur) this.tablefournisseur.getSelectionModel().getSelectedItem();//pour faire la selection d'un element du tableau
        if (fr != null) {
            this.idtextfield.setText(String.valueOf(fr.getCode()));//valueOf pour converti la variable au String
            this.nametextfield.setText(fr.getNom());
            this.adressetextfield.setText(fr.getAdresse());
            this.citytextfield.setText(fr.getVille());
            this.phonetextfield.setText(String.valueOf(fr.getTel()));
        }

    }


    @FXML
    void AjouterButtonAction(ActionEvent event) {
        Window owner = this.ajouterbutton.getScene().getWindow();
        String codefournisseur = this.idtextfield.getText();
        String nom = this.nametextfield.getText();
        String adresse = this.adressetextfield.getText();
        String ville = this.citytextfield.getText();
        String tel = this.phonetextfield.getText();
        if (!codefournisseur.isEmpty() && !nom.isEmpty() && !adresse.isEmpty() && !ville.isEmpty() && !tel.isEmpty()) {
            this.Connect();

            try {
                PreparedStatement preStmt = this.con.prepareStatement("insert into fournisseur (CodeFrnsr ,NomFrnsr , AdresseFrnsr ,VilleFrnsr ,TelFrnsr ) value (?, ?,?,?, ?)");
                try {
                    preStmt.executeUpdate("USE database1;");
                    preStmt.setString(1, codefournisseur);
                    preStmt.setString(2, nom);
                    preStmt.setString(3, adresse);
                    preStmt.setString(4, ville);
                    preStmt.setString(5, tel);
                    preStmt.executeUpdate();
                    this.con.close();
                } catch (Throwable var11) {
                    if (preStmt != null) {
                        try {
                            preStmt.close();
                        } catch (Throwable var10) {
                            var11.addSuppressed(var10);
                        }
                    }
                    throw var11;
                }
                if (preStmt != null) {
                    preStmt.close();
                }
            } catch (SQLException var12) {
                var12.printStackTrace();
                showAlert(Alert.AlertType.ERROR, owner, "ERROR!", "Les informations ne sont pas valides");
                return;
            }
            showAlert(Alert.AlertType.INFORMATION, owner, "Réussi", "Le fournisseur a été ajouté avec succès ");
            this.clearFields();
            this.showsFournisseur();
            this.RechercheFilter();

        } else {
            showAlert(Alert.AlertType.ERROR, owner, "ERROR!", "Complétez les infomation s'il vous plait");
        }
    }
    @FXML
    void ModifierButtonAction(ActionEvent event) {
        Window owner = this.modifierbutton.getScene().getWindow();

        String codefournisseur = this.idtextfield.getText();
        String nom = this.nametextfield.getText();
        String adresse = this.adressetextfield.getText();
        String ville = this.citytextfield.getText();
        String tel = this.phonetextfield.getText();
        if (!codefournisseur.isEmpty() && !nom.isEmpty() && !adresse.isEmpty() && !ville.isEmpty() && !tel.isEmpty()) {
            this.Connect();

            try {
                PreparedStatement preStmt = this.con.prepareStatement("update fournisseur set NomFrnsr=?, AdresseFrnsr=? ,VilleFrnsr =?,TelFrnsr=? where CodeFrnsr=? ");

                try {
                    preStmt.executeUpdate("USE database1;");
                    preStmt.setString(1, nom);
                    preStmt.setString(2, adresse);
                    preStmt.setString(3, ville);
                    preStmt.setString(4, tel);
                    preStmt.setString(5, codefournisseur);
                    preStmt.executeUpdate();
                    this.con.close();
                } catch (Throwable var11) {
                    if (preStmt != null) {
                        try {
                            preStmt.close();
                        } catch (Throwable var10) {
                            var11.addSuppressed(var10);
                        }
                    }

                    throw var11;
                }

                if (preStmt != null) {
                    preStmt.close();
                }
            } catch (SQLException var12) {
                var12.printStackTrace();
                showAlert(Alert.AlertType.ERROR, owner, "ERROR!", "Les informations ne sont pas valides");
                return;
            }

            showAlert(Alert.AlertType.INFORMATION, owner, "Réussi", "Le fournisseur a été modifié avec succès ");
            this.showsFournisseur();
            this.RechercheFilter();
            this.clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, owner, "ERROR!", "sélectioner le fournisseur a modifié");
        }
    }
    public ObservableList<classfournisseur> getFournisseurList() {
        ObservableList<classfournisseur> ListView = FXCollections.observableArrayList();
        this.Connect();
        String sql = "select * from fournisseur ";

        try {
            Statement stm = this.con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                ListView.add(new classfournisseur(rs.getString("CodeFrnsr"), rs.getString("NomFrnsr"), rs.getString("AdresseFrnsr"), rs.getString("VilleFrnsr"), rs.getString("TelFrnsr")));
            }

            this.con.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return ListView;
    }
    @FXML
    public void showsFournisseur() {
        ObservableList<classfournisseur> list = this.getFournisseurList();
        this.id_col.setCellValueFactory(new PropertyValueFactory<>("code"));
        this.nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        this.adresse_col.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        this.ville_col.setCellValueFactory(new PropertyValueFactory<>("ville"));
        this.tel_col.setCellValueFactory(new PropertyValueFactory<>("tel"));
        this.tablefournisseur.setItems(list);
    }
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText((String) null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    public void clearFields() {
        this.idtextfield.clear();
        this.nametextfield.clear();
        this.adressetextfield.clear();
        this.citytextfield.clear();
        this.phonetextfield.clear();
    }


    private void Connect() {
        DATABASE DBC = new DATABASE();
        DBC.Connect();
        this.con = DBC.con;
    }
    public void RechercheFilter() {
        ObservableList<classfournisseur> dataList = this.getFournisseurList();
        FilteredList<classfournisseur> filtreData = new FilteredList<>(dataList, b -> true);
        this.filterField.textProperty().addListener((observable, oldValue, newValue) ->
                filtreData.setPredicate(fournisseur -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (fournisseur.getAdresse().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (fournisseur.getNom().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (fournisseur.getVille().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (fournisseur.getTel().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (fournisseur.getCode().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else
                        return false;

                }));
        SortedList<classfournisseur> sortedData = new SortedList<>(filtreData);
        sortedData.comparatorProperty().bind(tablefournisseur.comparatorProperty());
        tablefournisseur.setItems(sortedData);
    }
    public void ClearTextField(){ //vides les textfile
       // Window owner = this.clearButton.getScene().getWindow();
        clearFields();
        filterField.clear();


    }
    @FXML
    void SupprimerButtonAction(ActionEvent event) {

        ButtonType ok = new ButtonType("oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", new ButtonType[]{ok, cancel});
        alert.setHeaderText("Voulez-vous supprimer ce fournisseur");
        Window owner = this.supprimerbutton.getScene().getWindow();
        String sql = "delete from Fournisseur where CodeFrnsr=?";
        ObservableList<classfournisseur> Fourn = this.tablefournisseur.getSelectionModel().getSelectedItems();
        if (Fourn.size() == 0) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur", "Choisissez un fournisseur");
        } else {
            Optional<ButtonType> button = alert.showAndWait();
            if (button.get() == cancel) {
                this.showsFournisseur();
            }

            if (button.get() == ok) {
                this.Connect();

                try {
                    classfournisseur f = (classfournisseur) Fourn.get(0);
                    PreparedStatement preStmt = this.con.prepareStatement(sql);
                    preStmt.setString(1, f.getCode());
                    preStmt.executeUpdate();
                    this.con.close();
                } catch (SQLException var10) {
                    var10.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, owner, "Erreur", "Résseyer");
                }

                this.showsFournisseur();
                showAlert(Alert.AlertType.INFORMATION, owner, "réussi", "Le fournisseur a été supprimé");
            }

            this.clearFields();
            this.RechercheFilter();
        }
    }
    @FXML
    void Retourfournisseur(ActionEvent event) throws IOException {
        Stage stage = (Stage) Retourbutton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueil.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("les ventes");
        stage.setScene(scene);
        stage.show();
    }

}

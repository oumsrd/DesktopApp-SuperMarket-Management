package com.example.test;
import com.example.test.Product;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.sql.PreparedStatement;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class ProductController implements Initializable {

    @FXML
    private Button retourproduct;
    @FXML
    private Button addButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private TableColumn<Product, Integer> colprodid;
    @FXML
    private TableColumn<Product, Integer> frsid;
    @FXML
    private TableColumn<Product, String> nameprod;
    @FXML
    private TableColumn<Product, Integer> qtyid;
    @FXML
    private TableColumn<Product, Integer> priceid;
    @FXML
    private TextField Pricefield;

    @FXML
    private TextField Qtefield;

    @FXML
    private AnchorPane myChoice;

    @FXML
    private TextField namefield;
    @FXML
    private TextField prodId;
    @FXML
    private TextField fourniseurtextfield;
    @FXML
    private TextField filterField;

    @FXML

    private TableView<Product> table_load;
    private Connection connection = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.showsProducts();
        this.RechercheFilter();
    }

    @FXML
    public void onTableItemSelect() {
        Product pr = (Product) this.table_load.getSelectionModel().getSelectedItem();
        if (pr != null) {
            this.prodId.setText(String.valueOf(pr.getProductId()));
            this.fourniseurtextfield.setText(String.valueOf(pr.getFourniseur()));
            this.namefield.setText(pr.getProductName());
            this.Qtefield.setText(String.valueOf(pr.getProductQty()));
            this.Pricefield.setText(String.valueOf(pr.getProductPrice()));



        }

    }


    @FXML
    void retourbuttonProduct(ActionEvent event) throws IOException {
        Stage stage = (Stage) retourproduct.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueil.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),700,700);
        stage.setTitle("accueil");
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void addButton(ActionEvent event) {
        Window owner = this.addButton.getScene().getWindow();
        String id = this.prodId.getText();
        String nom=this.namefield.getText();
        String fourniseur=this.fourniseurtextfield.getText();
        String quantite= this.Qtefield.getText();
        String prix= this.Pricefield.getText();

        if (!id.isEmpty() && !nom.isEmpty() && !fourniseur.isEmpty()  && !quantite.isEmpty() && !prix.isEmpty()  ) {
            this.Connect();

            try {
                PreparedStatement preStmt = this.connection.prepareStatement("insert into products (prodid , fornID,prodnom ,qty,price ) value (?, ?,?,?,?)");
                try {
                    preStmt.executeUpdate("USE database1;");
                    preStmt.setString(1, id);
                    preStmt.setString(2, fourniseur);
                    preStmt.setString(3, nom);
                    preStmt.setString(4,quantite );
                    preStmt.setString(5, prix);

                    preStmt.executeUpdate();
                    this.connection.close();
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
                showAlert(Alert.AlertType.ERROR, owner, "ERROR!", "Invalid Informations");
                return;
            }
            showAlert(Alert.AlertType.INFORMATION, owner, "Réussi", "Product Added Successfully! ");
            this.clearButton();
            this.showsProducts();
            this.RechercheFilter();

        } else {
            showAlert(Alert.AlertType.ERROR, owner, "ERROR!", "Complete informations please!");
        }
    }

    public ObservableList<Product> getProductsList() {
        ObservableList<Product> ListView = FXCollections.observableArrayList();
        this.Connect();
        String sql = "select * from products";

        try {
            Statement stm = this.connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                ListView.add(new Product(rs.getInt("prodid"),  rs.getInt("fornID"),rs.getString("prodnom"), rs.getInt("qty"), rs.getInt("price")));

            }

            this.connection.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return ListView;
    }

    @FXML
    public void showsProducts() {
        ObservableList<Product> list = this.getProductsList();
        this.colprodid.setCellValueFactory(new PropertyValueFactory<>("productId"));//
        this.frsid.setCellValueFactory(new PropertyValueFactory<>("fourniseur"));
        this.nameprod.setCellValueFactory(new PropertyValueFactory<>("productName"));
        this.qtyid.setCellValueFactory(new PropertyValueFactory<>("productQty"));
        this.priceid.setCellValueFactory(new PropertyValueFactory<>("productPrice"));


        this.table_load.setItems(list);
    }

    @FXML
    void editButton(ActionEvent event) {
        Window owner = this.filterField.getScene().getWindow();

        String id = this.prodId.getText();
        String fournisseur = this.fourniseurtextfield.getText();
        String nom = this.namefield.getText();
        String quantite = this.Qtefield.getText();
        String prix = this.Pricefield.getText();

        if (!id.isEmpty() && !nom.isEmpty() && !quantite.isEmpty() && !fournisseur.isEmpty() &&  !prix.isEmpty() ) {
            this.Connect();

            try {
                PreparedStatement preStmt = this.connection.prepareStatement("update products set fornID=?, prodnom =? ,qty =?,price=? where prodid =? ");

                try {
                    preStmt.executeUpdate("USE database1;");
                    preStmt.setString(5, id);
                    preStmt.setString(1, fournisseur);
                    preStmt.setString(2, nom);
                    preStmt.setString(3, quantite);
                    preStmt.setString(4, prix);
                    preStmt.executeUpdate();
                    this.connection.close();
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
                showAlert(Alert.AlertType.ERROR, owner, "ERROR!", "Invalid Information");
                return;
            }

            showAlert(Alert.AlertType.INFORMATION, owner, "Réussi", "Product Modified Succesully! ");
            this.showsProducts();
            this.RechercheFilter();
            this.clearButton();
        } else {
            showAlert(Alert.AlertType.ERROR, owner, "ERROR!", "Select Product to Modify");
        }
    }

    @FXML
    void deleteButton(ActionEvent event) {
        ButtonType ok = new ButtonType("oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", new ButtonType[]{ok, cancel});
        alert.setHeaderText("Are you sure to delete this product?");
        Window owner = this.deleteButton.getScene().getWindow();
        String sql = "delete from products where prodid=?";
        ObservableList<Product> Fourn = this.table_load.getSelectionModel().getSelectedItems();
        if (Fourn.size() == 0) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur", "Choose a product");
        } else {
            Optional<ButtonType> button = alert.showAndWait();
            if (button.get() == cancel) {
                this.showsProducts();
            }

            if (button.get() == ok) {
                this.Connect();

                try {
                    Product p = (Product) Fourn.get(0);
                    PreparedStatement preStmt = this.connection.prepareStatement(sql);
                    preStmt.setInt(1, p.getProductId());
                    preStmt.executeUpdate();
                    this.connection.close();
                } catch (SQLException var10) {
                    var10.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, owner, "Erreur", "Try Again");
                }

                this.showsProducts();
                showAlert(Alert.AlertType.INFORMATION, owner, "réussi", "Product Deleted ");
            }

            this.clearButton();
            this.RechercheFilter();
        }
    }

    private void Connect() {
        DatabaseConnection DBC = new DatabaseConnection();
        DBC.getConnection();
        this.connection = DBC. databaseLink;
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText((String) null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void clearButton() {
        this.prodId.clear();
        this.namefield.clear();
        this.fourniseurtextfield.clear();
        this.Qtefield.clear();
        this.Pricefield.clear();
    }

    public void RechercheFilter() {
        ObservableList<Product> dataList = this.getProductsList();
        FilteredList<Product> filtreData = new FilteredList<>(dataList, b -> true);
        this.filterField.textProperty().addListener((observable, oldValue, newValue) ->
                filtreData.setPredicate(Product -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (Product.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;

                    } else if (String.valueOf(Product.getProductId()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(Product.getFourniseur()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(Product.getProductPrice()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(Product.getProductQty()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }else {
                        return false;
                    }
                    //

                }));
        SortedList<Product> sortedData = new SortedList<>(filtreData);
        sortedData.comparatorProperty().bind(table_load.comparatorProperty());
        table_load.setItems(sortedData);
    }
    public void ClearTextField(){ //vides les textfile
        clearButton();
        filterField.clear();


    }

}



package com.example.test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class VenteController implements Initializable {
    @FXML
    private AnchorPane interfaceTitre1;
    @FXML
    private DatePicker date;

    @FXML
    private TextField id_ventetxt;

    @FXML
    private TableColumn<Vente, Integer> idcol;

    @FXML
    private ImageView imghomme;
    @FXML
    private TableColumn<Vente, Integer> prixtotalecol;

    @FXML
    private TextField prixtotaletxt;

    @FXML
    private TableColumn<Vente, Integer> prixunicol;

    @FXML
    private TextField prixunitairetext;

    @FXML
    private TableColumn<Vente, Integer> produitcol;

    @FXML
    private TextField produittext;

    @FXML
    private TextField quantiteVentetxt;

    @FXML
    private TableColumn<Vente, Integer> quantitecol;

    @FXML
    private TextField rechercheinput;

    @FXML
    private Button ajouterbutton;
    @FXML
    private TableColumn<Vente, Integer> remisecol;
    @FXML
    private TableColumn<Vente, Date> datecol;

    @FXML
    private TextField remiseventetxt;

    @FXML
    private TableView<Vente> tableVentes;

    @FXML
    private AnchorPane windowVentes;

    @FXML
    private AnchorPane windowtitles;

    @FXML
    private Button retourbutton;


    @FXML
    private Button modifierbutton;
    @FXML
    private Button recherchebutton;
    @FXML
    private Button supprimerbutton;

    private void Connect() {
        DATABASE DBC = new DATABASE();
        DBC.Connect();
    }

    public int prixTotalVente(int prix ,int quantite ,int remise){
        int prixSanRemise= prix*quantite ;


        return  prixSanRemise - (prixSanRemise*remise/100);

    }
    @FXML
    void ajouterproduitVente(ActionEvent event) {

        Window owner = ajouterbutton.getScene().getWindow();
        if (id_ventetxt.getText().isEmpty() && produittext.getText().isEmpty() && remiseventetxt.getText().isEmpty() && quantiteVentetxt.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur", "Complétez les informations s'il vous plait !");
        }else {


            int id_vente = Integer.parseInt(id_ventetxt.getText());
            int id_produit = Integer.parseInt(produittext.getText());
            LocalDate Date = date.getValue();
            int prix = Integer.parseInt(prixunitairetext.getText());
            int quantite = Integer.parseInt(quantiteVentetxt.getText());
            int remise = Integer.parseInt(remiseventetxt.getText());
            int prix_total = prixTotalVente(prix, quantite, remise);


            ButtonType ok = new ButtonType("oui", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ok, cancel);
            alert.setHeaderText("Voulez-vous ajouter cette vente");
            Optional<ButtonType> button = alert.showAndWait();
            if (button.get() == cancel) {

            }
            if (button.get() == ok) {

                Connect();

                String sql = "INSERT INTO vente(id_vente,produit,dateV,prixProd,quantite ,remise ,prixTotal) values ('" + id_vente + "','" + id_produit + "','" + Date.toString() + "','" + prix + "','" + quantite + "','" + remise + "','" + prix_total + "')";
                try {
                    Statement st = con.createStatement();
                    st.executeUpdate(sql);

                    showAlert(Alert.AlertType.CONFIRMATION, owner, "Ajout", "La vente est ajoutée avec succès ");

                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, owner, "ERREUR", "Les information ne sont pas valides !");
                    e.printStackTrace();

                }

            }


            showsVente();
            ClearTextField();

        }
    }
    public ObservableList<Vente> getVentList() {//retourner les champs de la base de donnés et lister dans ListView

        ObservableList<Vente> ListView = FXCollections.observableArrayList();
        Connect();
        Statement stm;
        String sql = "select * from vente";
        ResultSet rs;
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                ListView.add(
                        new Vente(Integer.parseInt(rs.getString("id_vente")),
                                Integer.parseInt(rs.getString("produit")),
                                Integer.parseInt(rs.getString("prixProd")),
                                Integer.parseInt(rs.getString("quantite")),
                                Integer.parseInt(rs.getString("remise")),
                                Integer.parseInt(rs.getString("prixTotal"))));


            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return ListView;
    }

    public void showsVente() {
        ObservableList<Vente> List = getVentList(); //appele la liste des vente
        idcol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("id_vente"));
        produitcol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("produit"));
        prixtotalecol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("prix_total"));
        prixunicol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("prix"));
        quantitecol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("quantite"));
        remisecol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("remise"));


        tableVentes.setItems(List);


    }

    public void ClearTextField() { //vides les textfile
        id_ventetxt.clear();
        produittext.clear();
        prixunitairetext.clear();
        quantiteVentetxt.clear();
        remiseventetxt.clear();
        prixtotaletxt.clear();
        date.setValue(null);
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    @FXML
    void modifierproduitVente(ActionEvent event) {

        Vente vente = tableVentes.getSelectionModel().getSelectedItem();
        Window  owner=modifierbutton.getScene().getWindow();
        if (vente == null) {
            showAlert(Alert.AlertType.WARNING,owner,"ERREUR","Choisissez une vente !");

        } else {
            int Id_Vente = Integer.parseInt(id_ventetxt.getText());
            LocalDate Date = date.getValue();
            int quantite = Integer.parseInt(quantiteVentetxt.getText());
            int prixUnitaire = Integer.parseInt(prixunitairetext.getText());
            int remise = Integer.parseInt(remiseventetxt.getText());
            int prixTotal=prixTotalVente(prixUnitaire,quantite,remise);
            int produit = Integer.parseInt(produittext.getText());


            try {
                Connect();
                Statement st = con.createStatement();
                PreparedStatement preStmt = con.prepareStatement("UPDATE vente SET quantite=? ,remise =? ,prixTotal=? ,produit=?  WHERE id_vente='" + vente.getId_vente() + "'");

                preStmt.setInt(1, quantite);
                preStmt.setInt(2,remise);
                preStmt.setInt(3,prixTotal);
                preStmt.setInt(4,produit);
                preStmt.executeUpdate();
                showAlert(Alert.AlertType.CONFIRMATION,owner,"Modification","la vente a été modifiée avec succès ");

            }
            catch (Exception e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR,owner,"ERREUR","Vous ne pouvez pas modifier cette vente");
            }

        }
        showsVente();
        //ClearTextField();
    }

    @FXML
    void rechercheproduitVente(ActionEvent event) throws SQLException {
        produitcol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("produit"));
        idcol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("id_vente"));
        prixtotalecol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("prix_total"));
        prixunicol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("prix"));
        quantitecol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("quantite"));
        remisecol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("remise"));


        Window owner = recherchebutton.getScene().getWindow();

        if (rechercheinput.getText().isEmpty()) { //si aucune valeur n'est pas saisi affiche un Alert
            showAlert(Alert.AlertType.WARNING, owner, "ERROR!",
                    "Saisissez un champs");
        } else {
            Statement stm = con.createStatement();

            try {

                ResultSet rs = stm.executeQuery("SELECT * FROM vente WHERE  produit = '" + rechercheinput.getText() + "'");

                ObservableList<Vente> data = FXCollections.observableArrayList();
                while (rs.next()) {
                    data.add(
                            new Vente(Integer.parseInt(rs.getString("id_vente")),
                                    Integer.parseInt(rs.getString("produit")),
                                    Integer.parseInt(rs.getString("prixProd")),
                                    Integer.parseInt(rs.getString("quantite")),
                                    Integer.parseInt(rs.getString("remise")),
                                    Integer.parseInt(rs.getString("prixTotal"))));



                }

                tableVentes.setItems(data);


            }
            catch (SQLException e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR,owner,"ERREUR","Saisissez les informations une autre fois");

            }

        }

    }



    @FXML
    void retourbuttonvente(ActionEvent event) throws IOException {
        Stage stage = (Stage) retourbutton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueil.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("accueil");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void supprimerproduitVente(ActionEvent event) {

        Vente vente=tableVentes.getSelectionModel().getSelectedItem();
        Window owner=supprimerbutton.getScene().getWindow();
        if(vente==null){

            showAlert(Alert.AlertType.ERROR,owner,"Erreur","Choisissez une vente");
        }
        else {
            ButtonType ok = new ButtonType("oui", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ok, cancel);
            alert.setHeaderText("Voulez-vous supprimer cette vente");
            Optional<ButtonType> button = alert.showAndWait();
            if (button.get() == cancel) {

            }
            if (button.get() == ok) {
                Connect();
                try {
                    Statement st = con.createStatement();
                    String sql = "DELETE FROM vente WHERE id_vente='" + vente.getId_vente() + "'";
                    st.executeUpdate(sql);
                    //con.close();


                    showAlert(Alert.AlertType.CONFIRMATION,owner,"Suppression","La vente a été supprimée avec succès");
                    showsVente();
                }
                catch (SQLException e){
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR,owner,"ERREUR","Vous ne pouvez pas supprimers cette vente");
                    showsVente();
                }
            }showsVente();
        }
        showsVente();
        ClearTextField();

    }

    DATABASE c = new DATABASE();
    Connection con = c.Connect();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showsVente();
        ClearTextField();
        ObservableList<Vente> List = getVentList();
        produitcol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("produit"));
        idcol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("id_vente"));
        prixtotalecol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("prix_total"));
        prixunicol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("prix"));
        quantitecol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("quantite"));
        remisecol.setCellValueFactory(new PropertyValueFactory<Vente, Integer>("remise"));

        tableVentes.setItems(List);


    }
    @FXML
    public void onTableItemSelect() {
        Vente vr = (Vente) this.tableVentes.getSelectionModel().getSelectedItem();//pour faire la selection d'un element du tableau
        if (vr != null) {
            this.id_ventetxt.setText(String.valueOf(vr.getId_vente()));//valueOf pour converti la variable au String
            this.prixtotaletxt.setText(String.valueOf(vr.getPrix_total()));
            this.prixunitairetext.setText(String.valueOf(vr.getPrix()));
            this. produittext.setText(String.valueOf(vr.getProduit()));
            this.quantiteVentetxt.setText(String.valueOf(vr.getQuantite()));
            this.remiseventetxt.setText(String.valueOf(vr.getRemise()));
            //this.date.setValue(LocalDate.parse(String.valueOf(vr.getDate())));



        }

    }
}



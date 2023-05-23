package com.example.test;

import com.example.test.Achat;
import com.example.test.DATABASE;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AchatController implements Initializable {

    @FXML
    private Button RetourButton;
    @FXML
    private TextField prixTotal;
    @FXML
    private TextField achatIdText;
    @FXML
    private Button AjouterButton;

    @FXML
    private TextField Id_AchatText;

    @FXML
    private Button ModifierButton;
    @FXML
    private Button ClearButton;

    @FXML
    private TextField RecherchInput;

    @FXML
    private Button RechercheButton;

    @FXML
    private Button  supprimerButton;

    @FXML
    private TableView<Achat> TableAchat;

    @FXML
    private final ComboBox<String> TypeRecherche =new ComboBox<String>() ;


    @FXML
    private TableColumn<Achat, Date> dateCol;

    @FXML
    private DatePicker date_Achat;

    @FXML
    private TableColumn<Achat,Integer> idAchatCol;

    @FXML
    private AnchorPane pane_achat;

    @FXML
    private TableColumn<Achat,Integer> prixCol;

    @FXML
    private TextField prix_unitaireText;

    @FXML
    private TextField produitText;

    @FXML
    private TableColumn<Achat,Integer> id_produitCol;

    @FXML
    private TableColumn<Achat,Integer> quantiteCol;

    @FXML
    private TextField quantiteAchatText;

    @FXML
    private TableColumn<Achat,Integer> remiseCol;

    @FXML
    private TextField remiseAchatText;


    private Connection con=null;

    public AchatController() {
    }

    private void Connect(){
        DATABASE DBC = new DATABASE();
        DBC.Connect();
        con = DBC.con;


    }
    @FXML
    public void ajouterAchat(){
        Window owner=AjouterButton.getScene().getWindow();
        if(Id_AchatText.getText().isEmpty() ||produitText.getText().isEmpty()||remiseAchatText.getText().isEmpty()||quantiteAchatText.getText().isEmpty() || date_Achat.getValue()==null){
            showAlert(Alert.AlertType.ERROR,owner,"Erreur !","Complétez les informations s'il vous plait !");
        }else {

            int id_Achat = Integer.parseInt(Id_AchatText.getText());
            int id_produit = Integer.parseInt(produitText.getText());
            LocalDate Date = date_Achat.getValue();
            int quantite = Integer.parseInt(quantiteAchatText.getText());
            int remise = Integer.parseInt(remiseAchatText.getText());
            int prix = Integer.parseInt(prix_unitaireText.getText());

            ButtonType ok = new ButtonType("oui", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ok, cancel);
            alert.setHeaderText("Voulez-vous ajouter cette Achat ?");
            Optional<ButtonType> button = alert.showAndWait();

            if (button.get() == cancel) {

            }
            if (button.get() == ok) {
                Connect();


                String sql = "INSERT INTO Achat (id_achat,produit, dateV,prixProd,quantite ,remise ) values ('" + id_Achat + "','" + id_produit + "','" + Date + "','" + prix + "','" + quantite + "','" + remise + "')";
                try {
                    Statement st = con.createStatement();
                    st.executeUpdate(sql);
                    //con.close();
                    showAlert(Alert.AlertType.CONFIRMATION, owner, "Ajout", "L'achat est ajoutée avec succès ");

                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, owner, "ERREUR", "Les information ne sont pas valides !");
                }

            }
            showsAchat();
            //ClearTextField1();

        }
    }
    void addQuantity(int id_produit ,int quantite){

        int stock= stock(id_produit);
        int  StockApresVente =stock+quantite;


        try {
            Connect();
            Statement st = con.createStatement();
            PreparedStatement preStmt = con.prepareStatement("UPDATE achat SET quantite =? WHERE prodId="+id_produit+"");

            preStmt.setInt(1, StockApresVente);

            preStmt.executeUpdate();
            //con.close();
            System.out.println("le stock a ete bien mise a jour ");


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    void substractQuantity(int id_produit ,int quantite){

        int stock= stock(id_produit);
        int  StockApresVente =stock-quantite;


        try {
            Connect();
            Statement st = con.createStatement();
            PreparedStatement preStmt = con.prepareStatement("UPDATE achat SET quantite =? WHERE prodId="+id_produit+"");

            preStmt.setInt(1, StockApresVente);

            preStmt.executeUpdate();
            //con.close();
            System.out.println("le stock a ete bien mise a jour ");


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    int stock(int id_produit ){
        String sql1="select quantite from achat where prodId="+id_produit+"";
        int stock=0;
        try{
            Connect();
            Statement st=con.createStatement();
            ResultSet result =st.executeQuery(sql1);
            while(result.next()){
                stock=result.getInt("quantite");
            }

            //con.close();
        }catch (SQLException E){

            E.printStackTrace();
        }
        return stock;
    }
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void showsAchat(){
        ObservableList<Achat> list = getAchatList(); //appele la liste des vente
        idAchatCol.setCellValueFactory(new PropertyValueFactory<>("id_Achat"));
        id_produitCol.setCellValueFactory(new PropertyValueFactory<>("produit"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        remiseCol.setCellValueFactory(new PropertyValueFactory<>("remise"));
        TableAchat.setItems(list);


    }

    private ObservableList<Achat> getAchatList() {
        ObservableList<Achat> ListView = FXCollections.observableArrayList();
        Connect();
        Statement stm;
        String sql ="select * from Achat ";
        ResultSet rs;
        try {
            stm=con.createStatement();
            rs=stm.executeQuery(sql);
            while (rs.next()){
                ListView.add(
                        new Achat(Integer.parseInt(rs.getString("id_achat")),
                                Integer.parseInt(rs.getString("produit")),
                                rs.getDate("dateV") ,
                                Integer.parseInt(rs.getString("prixProd"))  ,
                                Integer.parseInt(rs.getString("quantite")),
                                Integer.parseInt(rs.getString("remise"))));


            }
            //con.close();
        }catch (Exception e){
            e.printStackTrace();

        }
        return ListView ;
    }


    @FXML
    public void supprimerAchat(){
        Achat achat=TableAchat.getSelectionModel().getSelectedItem();
        Window owner= supprimerButton.getScene().getWindow();
        if(achat==null){

            showAlert(Alert.AlertType.ERROR,owner,"Erreur","Choisissez votre achat");
        }
        else {
            ButtonType ok = new ButtonType("oui", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ok, cancel);
            alert.setHeaderText("Voulez-vous supprimer cette achat");
            Optional<ButtonType> button = alert.showAndWait();
            if (button.get() == cancel) {

            }
            if (button.get() == ok) {
                Connect();
                try {
                    Statement st = con.createStatement();
                    String sql = "DELETE FROM achat WHERE id_achat='" + achat.getId_Achat() + "'";
                    st.executeUpdate(sql);
                    //con.close();


                    showAlert(Alert.AlertType.CONFIRMATION,owner,"Suppression","L' achat a été supprimée avec succès");
                    showsAchat();
                }
                catch (SQLException e){
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR,owner,"ERREUR","Vous ne pouvez pas supprimers cet achat");
                    showsAchat();
                }
            }showsAchat();
        }
        showsAchat();
        //ClearTextField();

    }



    @FXML
    public void modifierAchat(){
        Achat achat= TableAchat.getSelectionModel().getSelectedItem();
        Window  owner=ModifierButton.getScene().getWindow();
        if (achat == null) {
            showAlert(Alert.AlertType.WARNING,owner,"ERREUR","Choisissez votre achat !");

        } else {
            int id_Achat = Integer.parseInt(Id_AchatText.getText());
            int id_produit = Integer.parseInt(produitText.getText());
            LocalDate Date = date_Achat.getValue();
            int quantite = Integer.parseInt(quantiteAchatText.getText());
            int remise = Integer.parseInt(remiseAchatText.getText());
            int prix = Integer.parseInt(prix_unitaireText.getText());

            try {
                Connect();
                Statement st = con.createStatement();
                PreparedStatement preStmt = con.prepareStatement("UPDATE achat SET id_Achat=?,produit=? ,quantite=? ,prixProd=? ,remise =?   WHERE id_Achat='" + achat.getId_Achat() + "'");

                preStmt.setInt(1, id_Achat);
                preStmt.setInt(2, id_produit);
                preStmt.setInt(3, quantite);
                preStmt.setInt(4, prix);
                preStmt.setInt(5,remise);
                preStmt.executeUpdate();
                showAlert(Alert.AlertType.CONFIRMATION,owner,"Modification","l'achat a été modifiée avec succès ");

            }
            catch (Exception e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR,owner,"ERREUR","Vous ne pouvez pas modifier cet achat");
            }

        }
        showsAchat();
        //ClearTextField();
    }
    @FXML
    public void actualiser(){
        //vider les textfile et recherche input
        ClearTextField1();
        achatIdText.clear();
        prixTotal.clear();
        RecherchInput.clear();
        showsAchat();//afficher table avant recherche

    }
    public void ajoutProd() throws SQLException {
        Connect();
        int id = Integer.parseInt(produitText.getText());
        int quantite=Integer.parseInt(quantiteAchatText.getText());
        int remise=Integer.parseInt(remiseAchatText.getText());
        int prix=Integer.parseInt(prix_unitaireText.getText());

        Statement stm = con.createStatement();
        String sql = "select produit from achat where prodId ="+id;
        ResultSet rs = stm.executeQuery(sql);
        while(!rs.next()){
            String sql1 ="INSERT INTO achat (id_achat,produit,prix,quantite ,remise ) values ('" + id + "','" + "newProd" + "','" + prix + "','" + quantite + "','" + remise + "')";
            stm.executeUpdate(sql1);
            //con.close();
        }
    }
    public void searchAchat() throws SQLException {
        idAchatCol.setCellValueFactory(new PropertyValueFactory<Achat, Integer>("id_Achat"));
        id_produitCol.setCellValueFactory(new PropertyValueFactory<Achat, Integer>("produit"));
        quantiteCol.setCellValueFactory(new PropertyValueFactory<Achat, Integer>("quantite"));
        prixCol.setCellValueFactory(new PropertyValueFactory<Achat, Integer>("prix"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Achat, Date>("date"));
        remiseCol.setCellValueFactory(new PropertyValueFactory<Achat, Integer>("remise"));


        Window owner = RechercheButton.getScene().getWindow();

        if (RecherchInput.getText().isEmpty()) { //si aucune valeur n'est pas saisi affiche un Alert
            showAlert(Alert.AlertType.WARNING, owner, "ERROR!",
                    "Saisissez un champs");
        } else {
            Statement stm = con.createStatement();

            try {

                ResultSet rs = stm.executeQuery("SELECT * FROM achat WHERE id_achat  = '" + RecherchInput.getText() + "'");

                ObservableList<Achat> data = FXCollections.observableArrayList();
                while (rs.next()) {
                    data.add(
                            new Achat(Integer.parseInt(rs.getString("id_achat")),
                                    Integer.parseInt(rs.getString("produit")),
                                    Integer.parseInt(rs.getString("prixProd")),
                                    Integer.parseInt(rs.getString("quantite")),
                                    Integer.parseInt(rs.getString("remise"))));




                }

                TableAchat.setItems(data);


            }
            catch (SQLException e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR,owner,"ERREUR","Saisissez les informations une autre fois");

            }

        }

    }
    @FXML
    public void printPrixTotal() throws SQLException {
        Connect();
        int id = Integer.parseInt(achatIdText.getText());
        Statement stm = con.createStatement();

        String sql = "select prixProd,quantite from Achat where id_achat ="+id;
        ResultSet rs = stm.executeQuery(sql);
        while(rs.next()){
            int prixF = Integer.parseInt(rs.getString("prixProd"));
            int quantiteF = Integer.parseInt(rs.getString("quantite"));

            int prixtotal = prixF * quantiteF ;
            prixTotal.setText(Integer.toString(prixtotal));

        }
    }

    // Remplire  les textField par les donnes de ligne selectioné
    @FXML
    public void onTableItemSelect (){
        Achat v = TableAchat.getSelectionModel().getSelectedItem();
        if (v !=null) {
            Id_AchatText.setText(String.valueOf(v.getId_Achat()));
            produitText.setText((String.valueOf(v.getProduit())));

            prix_unitaireText.setText(String.valueOf(v.getPrix()));
            quantiteAchatText.setText((String.valueOf(v.getQuantite())) );
            remiseAchatText.setText(String.valueOf((v.getRemise())));
            date_Achat.setValue(LocalDate.parse(String.valueOf(v.getDate())));
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showsAchat();
        ClearTextField1();
        // data liste des types de recherche
        ObservableList<String> data = FXCollections.observableArrayList("id_achat","produit","dateV","prixProd","quantite","Remise","prixTotal");
        TypeRecherche.setItems(data);
    }

    @FXML
    void RetourAchat(ActionEvent event) throws IOException {
        Stage stage = (Stage) RetourButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("accueil.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("les ventes");
        stage.setScene(scene);
        stage.show();

    }
    public void ClearTextField1(){ //vides les textfile
        Id_AchatText.clear();
        produitText.clear();
        prix_unitaireText.clear();
        quantiteAchatText.clear();
        remiseAchatText.clear();
        date_Achat.setValue(null);

    }





}
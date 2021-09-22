package ListMembre;

import AddBook.Controller;
import AddMembre.Addmembre;
import Databases.Databases;
import ListBook.Liste;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListeMembre implements Initializable {
    @FXML
    TableView<Membr> tableview;

    @FXML
    private TableColumn<Membr, String> id;

    @FXML
    private TableColumn<Membr, String> name;

    @FXML
    private TableColumn<Membr, String> mobile;

    @FXML
    private TableColumn<Membr, String> email;

    ObservableList<Membr> list= FXCollections.observableArrayList();








    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            initCol();
            loaddata();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    void initCol()
    {
        id.setCellValueFactory(new PropertyValueFactory<>("membreid"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    void loaddata() throws SQLException, ClassNotFoundException {
        list.clear();
         Databases databases=new Databases();
         String req="SELECT * FROM membre ";

         ResultSet s=databases.execQuery(req);
         while(s.next())
         {

             String membreid=s.getString("membreId");
             String nam=s.getString("name");
             String m=s.getString("mobile");
             String e=s.getString("email");
             list.add(new Membr(nam,membreid,m,e));
         }
         tableview.getItems().setAll(list);
    }
    @FXML
    public void deleteM(ActionEvent actionEvent) throws SQLException {

        Membr selectedForDeletion=tableview.getSelectionModel().getSelectedItem();




        if(selectedForDeletion==null)
        {

            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
            alert3.setHeaderText(null);
            alert3.setContentText("Please Select Book");
            alert3.showAndWait();
            return;

        }

        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting Membre");
        alert.setContentText("are you sure want To Delete Membre "+selectedForDeletion.getName()+"?");
        Optional<ButtonType> answer=alert.showAndWait();

        if(answer.get()==ButtonType.OK)
        {

            String req="delete from book where id='"+selectedForDeletion.getMembreid()+"'";
            if(Databases.execAction(req))
            {
                Databases.statement.executeUpdate(req);
                alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succus");
                alert.setHeaderText(null);
                alert.setContentText("Book has been Deleted");
                alert.showAndWait();
                list.remove(selectedForDeletion);
            }



        }
        else{
            Alert alert2;
            alert2=new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Cancelled");
            alert2.setHeaderText(null);
            alert2.setContentText("Delete Operation Cancelled");
            alert2.showAndWait();
        }
    }
    @FXML
    public void refreshM(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loaddata();
    }
     @FXML
    public void editM(ActionEvent actionEvent) throws IOException {
         Membr selectedForEdit = tableview.getSelectionModel().getSelectedItem();
         if (selectedForEdit == null) {

             Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
             alert3.setHeaderText(null);
             alert3.setContentText("Please Select Book");
             alert3.showAndWait();
             return;

         }

         try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddMembre/addmembre.fxml"));
             Parent parent = loader.load();
             Addmembre addMembre = (Addmembre) loader.getController();

             addMembre.infalteM(selectedForEdit);
             Stage stage = new Stage(StageStyle.DECORATED);
             stage.setTitle("Edit Book");
             stage.setScene(new Scene(parent));
             stage.show();


             stage.setOnCloseRequest((e) ->
             {
                 //refresh(new ActionEvent());


             });
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

         public static class Membr
    {
        private String name;
        private SimpleStringProperty membreid;
        private SimpleStringProperty mobile;
        private SimpleStringProperty email;

        public Membr(String name, String membreid, String mobile, String email) {
            this.name = name;
            this.membreid = new SimpleStringProperty(membreid);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
        }



        public String getName() {
            return name;
        }



        public String getMembreid() {
            return membreid.get();
        }



        public String getMobile() {
            return mobile.get();
        }



        public String getEmail() {
            return email.get();
        }









    }


}



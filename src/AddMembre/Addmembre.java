package AddMembre;

import Databases.Databases;
import ListBook.Liste;
import ListMembre.ListeMembre;
import ListMembre.Membre;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Addmembre implements Initializable {
       Databases databases;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField membreID;

    @FXML
    private JFXTextField mobile;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXButton save;

    @FXML
    private JFXButton cancel;
    Boolean isInEdit=Boolean.FALSE;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void saveMembre() throws SQLException, ClassNotFoundException {
        String id=membreID.getText();
        String nam=name.getText();
        String mob=mobile.getText();
        String Em=email.getText();
        if(id.isEmpty()||nam.isEmpty()||mob.isEmpty()||Em.isEmpty()){

        }
          if(isInEdit)
          {
              editOption();
              return;
          }
        databases=new Databases();
        String req="insert into membre values('"+id+"','"+nam+"','"+mob+"','"+Em+"')";
        databases.insert(req);
    }

    @FXML
    void cancelMembre()
    {

    }


    public void infalteM(ListeMembre.Membr membre)
    {
        name.setText(membre.getName());
         membreID.setEditable(false);
        membreID.setText(membre.getMembreid());
        email.setText(membre.getEmail());
        mobile.setText(membre.getMobile());
        isInEdit=Boolean.TRUE;

    }

    public void editOption() throws SQLException {
        ListeMembre.Membr  membr= new ListeMembre.Membr(name.getText(),membreID.getText(),mobile.getText(),email.getText());
        Alert alert;
        if(databases.upadateMembre(membr))
        {
            alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("UPDATE MEMBRE SUCCUS ");
            alert.showAndWait();


        }
        else {
            alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("UPDATE MEMBRE Failed ");
            alert.showAndWait();
        }
    }

}

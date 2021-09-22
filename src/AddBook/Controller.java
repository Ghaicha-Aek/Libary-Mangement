package AddBook;

import Databases.Databases;
import ListBook.Liste;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.org.apache.bcel.internal.generic.ALOAD;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import org.apache.derby.database.Database;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller implements Initializable {


          Databases database;
@FXML
AnchorPane rootPane;


    @FXML
        private JFXTextField Title;

        @FXML
        private JFXTextField id;

        @FXML
        private JFXTextField author;

        @FXML
        private JFXTextField publisher;

        @FXML
        private JFXButton save;

        @FXML
        private JFXButton cancel;
         String req;

         Boolean isInEdit=Boolean.FALSE;

        @FXML
        void cancelBook(ActionEvent event) {
           Stage stage= (Stage)rootPane.getScene().getWindow();
            stage.close();
        }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            database=new Databases();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void savebook(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        String bookid=id.getText();
        String bookauthor=author.getText();
        String bookpublisher=publisher.getText();
        String bookTitle=Title.getText();
        if (bookid.isEmpty() || bookauthor.isEmpty() || bookpublisher.isEmpty() || bookTitle.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("please enter all information");
            alert.showAndWait();
        }

        if(isInEdit)
        {
            editOperation();
            return;
        }
        else
        {
            req = "insert into book values ('"+bookid+"', '"+bookTitle+"', '"+bookauthor+"', '"+bookpublisher+"', '1');";


            database.insert(req);

        }



    }


    public void infalte(Liste.Book liste)
    {
        Title.setText(liste.getTitle());
        id.setEditable(false);
        id.setText(liste.getId());
        author.setText(liste.getAuthor());
        publisher.setText(liste.getPublisher());
        isInEdit=Boolean.TRUE;



    }









    public void editOperation()
    {
        Liste.Book book = new Liste.Book(Title.getText(),id.getText(),author.getText(),publisher.getText(),true);
        if(database.updateBook(book))
        {
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Updated Book ");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else
        {
            Alert alert2=new Alert(Alert.AlertType.ERROR);
            alert2.setHeaderText(null);
            alert2.setContentText("Failed Upadate");
            alert2.showAndWait();

        }

    }
}



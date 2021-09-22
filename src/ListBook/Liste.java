package ListBook;

import AddBook.Controller;
import Databases.Databases;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.crypto.Data;
import java.awt.print.Book;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static Databases.Databases.*;

public class Liste implements Initializable {


          ObservableList<Book> list= FXCollections.observableArrayList();

         @FXML
         private TableView<Book> tableview;
        @FXML
        private AnchorPane rootPane;

        @FXML
        private TableColumn<Book, String> bookid;

        @FXML
        private TableColumn<Book, String> booktitle;

        @FXML
        private TableColumn<Book, String> author;

        @FXML
        private TableColumn<Book, String> publisher;

        @FXML
        private TableColumn<Book, Boolean> avail;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            initCol();
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void initCol()
    {
        bookid.setCellValueFactory(new PropertyValueFactory<>("id"));
        booktitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        avail.setCellValueFactory(new PropertyValueFactory<>("avail"));
    }

    void loadData() throws SQLException, ClassNotFoundException {
        list.clear();
          Databases databases =new Databases();

          String requete="SELECT * FROM book";
          ResultSet rs= execQuery(requete);
          while (rs.next())
          {
              String Bookid=rs.getString("id");
              String Title=rs.getString("title");

              String author=rs.getString("author");
              String publisher=rs.getString("publisher");
              Boolean avail=rs.getBoolean("isAvail");
              list.add(new Book(Title,Bookid,author,publisher,avail));
          }

        tableview.setItems(list);
    }

    @FXML
    public void Delete(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Book selectedForDeletion=tableview.getSelectionModel().getSelectedItem();




        if(selectedForDeletion==null)
        {

            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
            alert3.setHeaderText(null);
            alert3.setContentText("Please Select Book");
            alert3.showAndWait();
            return;

        }



          if(isBookAlready(selectedForDeletion))
          {
              Alert alert4=new Alert(Alert.AlertType.INFORMATION);
              alert4.setHeaderText(null);
              alert4.setContentText("Can't Delete Book ");
              alert4.show();
              return;
          }
           Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting Book");
            alert.setContentText("are you sure want To Delete BOOK "+selectedForDeletion.getTitle()+"?");
        Optional<ButtonType> answer=alert.showAndWait();

        if(answer.get()==ButtonType.OK)
        {

            String req="delete from book where id='"+selectedForDeletion.getId()+"'";
            if(Databases.execAction(req))
            {
                Databases.statement.executeUpdate(req);
                alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succus");
                alert.setHeaderText(null);
                alert.setContentText("Book has been Submitted");
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
    public void Edit(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Book selectedForEdit = tableview.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {

            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
            alert3.setHeaderText(null);
            alert3.setContentText("Please Select Book");
            alert3.showAndWait();
            return;

        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddBook/sample.fxml"));
            Parent parent = loader.load();
            Controller addBook = (Controller) loader.getController();

            addBook.infalte(selectedForEdit);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Book");
            stage.setScene(new Scene(parent));
            stage.show();


            stage.setOnCloseRequest((e) ->
            {
                refresh(new ActionEvent());


            });


        } finally {

        }
    }
    @FXML
    public void refresh(ActionEvent actionEvent)  {
        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static   class Book
    {






        private SimpleStringProperty title;
        private SimpleStringProperty id;
        private SimpleStringProperty author;
        private SimpleStringProperty publisher;
        private SimpleBooleanProperty avail;

        public String getTitle() {
            return title.get();
        }

        public SimpleStringProperty titleProperty() {
            return title;
        }

        public void setTitle(String title) {
            this.title.set(title);
        }

        public String getId() {
            return id.get();
        }

        public SimpleStringProperty idProperty() {
            return id;
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public String getAuthor() {
            return author.get();
        }

        public SimpleStringProperty authorProperty() {
            return author;
        }

        public void setAuthor(String author) {
            this.author.set(author);
        }

        public String getPublisher() {
            return publisher.get();
        }

        public SimpleStringProperty publisherProperty() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher.set(publisher);
        }

        public boolean isAvail() {
            return avail.get();
        }

        public SimpleBooleanProperty availProperty() {
            return avail;
        }

        public void setAvail(boolean avail) {
            this.avail.set(avail);
        }




        public Book(String title, String id, String author, String publisher, Boolean avail) {
            this.title = new SimpleStringProperty(title);
            this.id = new SimpleStringProperty(id);;
            this.author = new SimpleStringProperty(author);;
            this.publisher = new SimpleStringProperty(publisher);;
            this.avail = new SimpleBooleanProperty(avail);
        }



    }
}

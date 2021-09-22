package main;

import Databases.Databases;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class Main implements Initializable {


    @FXML
    private HBox BookInfo;

    @FXML
    private Text BookTtile;

    @FXML
    private Text AuthorName;

    @FXML
    private Text statusBook;

    @FXML
    private HBox MembreInfo;

    @FXML
    private TextField BookIdInput;


    @FXML
    private Text membreInfo;

    @FXML
    private Text ContactInfo;


    @FXML
    private TextField membreIDinput;


    @FXML
    private JFXTextField Bookid;
    @FXML
    private StackPane rootpane;



    @FXML
    private ListView<String> list;

    Boolean isReadyforSubmission=false;

     Databases databases= new Databases();

    public Main() throws SQLException, ClassNotFoundException {
    }


    @FXML
    void LoadAddBook(ActionEvent event) throws IOException {
        LoadWindows("/AddBook/sample.fxml","AddBook");

    }

    @FXML
    void LoadAddMembre(ActionEvent event) throws IOException {
        LoadWindows("/AddMembre/addmembre.fxml","AddMembre");

    }

    @FXML
    void LoadTableBook(ActionEvent event) throws IOException {
        LoadWindows("/ListBook/liste.fxml","TableBook");

    }

    @FXML
    void LoadTableMembre(ActionEvent event) throws IOException {
        LoadWindows("/ListMembre/ListeMembre.fxml","ListeMembre");
    }

    void LoadWindows(String loc,String title) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource(loc));
        Stage stage=new Stage(StageStyle.DECORATED);
        stage.setTitle(title);
        stage.setScene(new Scene(parent));
        stage.show();

    }

     @FXML
    void loadBookInfo() throws SQLException, ClassNotFoundException {
          LoadClearInfo();
         String id=BookIdInput.getText();
         String req="SELECT * FROM book WHERE id='"+id+"'";
         ResultSet rs=databases.execQuery(req);
         boolean flag=false;


         while(rs.next())
         {
             String name=rs.getNString("title");
             String author=rs.getString("author");
             Boolean status=rs.getBoolean("isAvail");
             BookTtile.setText(name);
             AuthorName.setText(author);
             String s=(status)?"available":"not available";
             statusBook.setText(s);
             flag=true;

         }
         if(!flag)
         {
             BookTtile.setText("No such Book Available");

         }

     }
    @FXML
    void LoadMembreInfo(ActionEvent event) throws SQLException, ClassNotFoundException {
        LoadClearMembre();
         String id=membreIDinput.getText();
         String requete="SELECT * FROM membre WHERE membreID='"+id+"'";
         ResultSet r=databases.execQuery(requete);
          Boolean flag=false;
         while (r.next())
         {
             String name=r.getNString("name");
             String c=r.getString("email");

             ContactInfo.setText(c);
             membreInfo.setText(name);

             flag=true;



         }

         if(!flag)
         {
             membreInfo.setText("no such Membre ");
         }
    }


    void LoadClearMembre()
    {
        membreInfo.setText("");
        ContactInfo.setText("");
    }
    void LoadClearInfo()
    {
        BookTtile.setText("");
        AuthorName.setText("");
        statusBook.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    void loadIssue(ActionEvent event) {
       String membre=membreIDinput.getText();
       String book=BookIdInput.getText();

       Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Confirmation Issue Operation ");
       alert.setHeaderText(null);
       alert.setContentText("are you sure want to issue the book "+BookTtile.getText()+"\n to "+membreInfo.getText());
        Optional<ButtonType> reponse=alert.showAndWait();
        if(reponse.get()==ButtonType.OK)
        {
            String str="insert into  issue(membreid,issueID) VALUES('"+membre+"','"+book+"')";

            String str2="UPDATE book SET isAvail=false where id='"+book+"'";
            if(databases.execAction(str)&&databases.execAction(str2))
            {
                Alert alert1 =new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success ");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Issue Complete ");
                alert1.showAndWait();
            }
            else
            {
                Alert alert2= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed");
                alert2.setHeaderText(null);
                alert2.setContentText("Issue operation Failed ");
                alert2.showAndWait();
            }
             }
        else {
            Alert alert3=new Alert(Alert.AlertType.INFORMATION);
            alert3.setTitle("Cancelled");
            alert3.setHeaderText(null);
            alert3.setContentText("Issue Operation Cancelled");
            alert3.showAndWait();
        }

    }


    @FXML
    void LoadMembreInfo2(ActionEvent event) throws SQLException, ClassNotFoundException {
        ObservableList<String> issuedata= FXCollections.observableArrayList();
           String bookid=Bookid.getText();

           String query="select * from issue where issueID='"+bookid+"'";
           ResultSet rs=databases.execQuery(query);

           while (rs.next())
           {
               String mbookid=bookid;
               String Mmembre=rs.getString("membreid");
               Timestamp timestamp=rs.getTimestamp("issueTime");
               int renewcount=rs.getInt("renew_count");
               issuedata.add("Issue Data and Time :"+timestamp.toGMTString());
               issuedata.add("renew Count :"+renewcount);

               issuedata.add("Book Information ");

               query="select * from book where id='"+mbookid+"'";
               ResultSet r1=databases.execQuery(query);
               while (r1.next())
               {
                   issuedata.add("Book Title : "+r1.getString("title"));
                   issuedata.add("Book Author :"+r1.getString("author"));
                   issuedata.add("Book Publisher :"+r1.getString("publisher"));

               }
               issuedata.add("Membre Information ");
               query="select * from membre where membreId='"+Mmembre+"'";
                r1=databases.execQuery(query);
                while (r1.next())
                {
                    issuedata.add("Membre Name :"+r1.getString("name"));
                    issuedata.add("Membre mobile :"+r1.getString("mobile"));
                    issuedata.add("Membre Email : "+r1.getString("email"));
                }
                isReadyforSubmission=true;
           }
           list.getItems().setAll(issuedata);

    }


    @FXML
    void loadSubmission(ActionEvent event) {
        Alert alert2;
         if(!isReadyforSubmission)
         {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("failed");
             alert.setHeaderText(null);
             alert.setContentText("please Select a book to sumbit ");
             alert.showAndWait();
             return;
         }
         String book=Bookid.getText();

         Alert alert1=new Alert(Alert.AlertType.CONFIRMATION);
         alert1.setTitle("Confirmation");
         alert1.setHeaderText(null);
         alert1.setContentText("Are you sure want to return "+Bookid.getText()+"\n to "+membreIDinput.getText());

         Optional<ButtonType> reponse=alert1.showAndWait();
                 if(reponse.get()==ButtonType.OK)
                 {
                     String qu1="delete from issue where issueID='"+Bookid.getText()+"'";
                     String qu2="UPDATE book SET isAvail=true where id='"+book+"'";

                     if(databases.execAction(qu1) && databases.execAction(qu2))
                     {
                          alert2=new Alert(Alert.AlertType.INFORMATION);
                         alert2.setTitle("Succus");
                         alert2.setHeaderText(null);
                         alert2.setContentText("Book has been Submitted");
                         alert2.showAndWait();
                     }

                     else
                     {
                         alert2=new Alert(Alert.AlertType.ERROR);
                         alert2.setTitle("FAILED ");
                         alert2.setHeaderText(null);
                         alert2.setContentText("Submission has Been failed");
                         alert2.showAndWait();

                     }
                 }
                 else {
                     alert2=new Alert(Alert.AlertType.INFORMATION);
                     alert2.setTitle("Cancelled");
                     alert2.setHeaderText(null);
                     alert2.setContentText("Submission Operation Cancelled");
                     alert2.showAndWait();
                 }
    }

    @FXML
    void LoadRenew(ActionEvent event) {
        if (Bookid.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("BookId is Empty ");
            alert.showAndWait();
            return;

        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to renew the Book ");

        Optional<ButtonType> response= alert.showAndWait();

        if(response.get()==ButtonType.OK)
        {
            String sc1="UPDATE issue SET issueTime=CURRENT_TIMESTAMP,renew_count=renew_count+1 where issueID='"+Bookid.getText()+"'";

            if(databases.execAction(sc1))
            {
                alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succus");
                alert.setHeaderText(null);
                alert.setContentText("Renew has been Submitted");
                alert.showAndWait();
            }
            else
            {
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("FAILED ");
                alert.setHeaderText(null);
                alert.setContentText("Renew has Been failed");
                alert.showAndWait();
            }


        }
        else{
           Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Cancelled");
            alert1.setHeaderText(null);
            alert1.setContentText("Submission Operation Cancelled");
            alert1.showAndWait();

        }


    }


    @FXML
    void menuAddMembre(ActionEvent event) throws IOException {
        LoadWindows("/AddMembre/addmembre.fxml","AddMembre");


    }

    @FXML
    void menuClose(ActionEvent event) {
        ((Stage)rootpane.getScene().getWindow()).close();

    }


    @FXML
    void menuAddBook(ActionEvent event) throws IOException {
        LoadWindows("/AddBook/sample.fxml","AddBook");


    }


    @FXML
    void menuViewsBook(ActionEvent event) throws IOException {
        LoadWindows("/ListBook/liste.fxml","TableBook");
    }

    @FXML
    void menuViewsMembre(ActionEvent event) throws IOException {
        LoadWindows("/ListMembre/ListeMembre.fxml","ListeMembre");

    }
}

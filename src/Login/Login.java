package Login;

import Settings.Preferences;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private Label TitleLabel;
    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField password;

    Preferences preferences;

    @FXML
    void Cancel(ActionEvent event) {

    }

    @FXML
    void login(ActionEvent event) throws IOException {
        String u=user.getText();
        String p=password.getText();

        if(u.equals(preferences.getUser()) && p.equals(preferences.getPassword()))
        {
          closeStage();
          LoadMain();
        }
        else
        {
            user.getStyleClass().add(".wrong-credentials");
            password.getStyleClass().add(".wrong-credentials");
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
             preferences=Preferences.getPreferences();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    void closeStage()
    {
        ((Stage)user.getScene().getWindow()).close();
    }

    void LoadMain() throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/main/main.fxml"));
        Stage stage=new Stage(StageStyle.DECORATED);
        stage.setTitle("Page Principale");
        stage.setScene(new Scene(parent));
        stage.show();

    }




}

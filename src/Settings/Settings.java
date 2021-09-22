package Settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Settings implements Initializable {

    @FXML
    private JFXTextField NoDays;

    @FXML
    private JFXTextField FDays;

    @FXML
    private JFXTextField user;

    @FXML
    private JFXPasswordField password;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initdefaultValues();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CancelledSettings(ActionEvent event) {

    }

    @FXML
    void saveSeetings(ActionEvent event) throws IOException, ClassNotFoundException {
        int days=Integer.parseInt(NoDays.getText());
        float fdays=Float.parseFloat(FDays.getText());
        String s=user.getText();
        String p=password.getText();


        Preferences preferences = new Preferences();
        preferences.setNoDays(days);
        preferences.setFDays(fdays);
        preferences.setUser(s);
        preferences.setPassword(p);
        Preferences.writeToFile(preferences);
    }


    private  void initdefaultValues() throws IOException, ClassNotFoundException {
        Preferences preferences = Preferences.getPreferences();
        NoDays.setText(String.valueOf(preferences.getNoDays()));
        FDays.setText(String.valueOf(preferences.getFDays()));
        user.setText(String.valueOf(preferences.getUser()));
        password.setText(String.valueOf(preferences.getPassword()));
    }


}

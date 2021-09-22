package Settings;

import com.google.gson.Gson;
import com.sun.istack.internal.logging.Logger;

import java.io.*;
import java.util.logging.Level;

public class  Preferences {


    int NoDays;


    float FDays;
    String user;
    String password;
     public  static  final String CONFIG_FILE="config.txt";

    public int getNoDays() {
        return NoDays;
    }

    public void setNoDays(int noDays) {
        NoDays = noDays;
    }

    public float getFDays() {
        return FDays;
    }

    public void setFDays(float FDays) {
        this.FDays = FDays;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Preferences() {
        NoDays = 14;
        FDays = 0;
        user = "admin";
        password = "admin";
    }

    public static void initConfig() throws IOException, ClassNotFoundException {
        Writer writer=null;

        try {
            Preferences preferences =new Preferences();
            Gson gson =new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences,writer);



        } catch (IOException e) {
            //Logger.getLogger(Class.forName(Preferences.class.getName())).log(Level.SEVERE,null, e);

        }finally {
            try {
                writer.close();

            } catch (Exception e) {
                //Logger.getLogger(Class.forName(Preferences.class.getName())).log(Level.SEVERE,null, e);

            }
        }

        }

        public static  Preferences getPreferences() throws IOException, ClassNotFoundException {
            Gson gson = new Gson();
            Preferences preferences = new Preferences();
            try {
                 preferences= gson.fromJson(new FileReader(CONFIG_FILE),Preferences.class);
            } catch (FileNotFoundException e) {
                initConfig();
            }
             return preferences;
        }








    public static void writeToFile(Preferences preferences) throws IOException, ClassNotFoundException
    {
        Writer writer=null;

        try {

            Gson gson =new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(preferences,writer);



        } catch (IOException e) {
            //Logger.getLogger(Class.forName(Preferences.class.getName())).log(Level.SEVERE,null, e);

        }finally {
            try {
                writer.close();

            } catch (Exception e) {
                //Logger.getLogger(Class.forName(Preferences.class.getName())).log(Level.SEVERE,null, e);

            }
        }

    }




    }




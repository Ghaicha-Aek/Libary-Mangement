package Databases;
import ListBook.Liste;
import ListMembre.ListeMembre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javax.swing.*;
import java.sql.*;
import java.sql.Statement;

//import java.sql.*;
//import  jdk.nashorn.internal.ir.Statement;

public class   Databases {

    private static Databases Handler;

    private static final String Db_url = "jdbc:derby:database;create=true";
    private static Connection conn = null;

    public static void setStatement(Statement statement) {
        Databases.statement = statement;
    }
/*
    public static Statement getStatement() {
        statement=conn.createStatement();

        return statement;
    }*/

    public static Statement statement = null;


    public Databases() throws SQLException, ClassNotFoundException {
        createConnection();
        //setupBookTable();
    }

    public static void createConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3307/libary";
        String name = "root";
        String pass = "root";

        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, name, pass);
        statement = conn.createStatement();


    }

    public static void closeConnection() throws SQLException {
        conn.close();
        statement.close();
    }

    public void insert(String requete) throws SQLException, ClassNotFoundException {
        createConnection();
        statement.executeUpdate(requete);
        closeConnection();


    }

    public static void delete(Object  object,String req) throws SQLException, ClassNotFoundException {

        createConnection();
        statement.executeUpdate(req);
        closeConnection();
    }

    public void selectall(String requete) throws SQLException {
        statement = conn.createStatement();

        ResultSet rs = statement.executeQuery(requete);


    }

    public static ResultSet execQuery(String req) throws SQLException, ClassNotFoundException {
        ResultSet r;

            createConnection();
            statement=conn.createStatement();
            r = statement.executeQuery(req);




             return r;


    }


  public static  boolean execAction(String action)  {
      try {
         // createConnection();
          statement=conn.createStatement();
          statement.execute(action);
          return true;
      } catch (SQLException e) {
          JOptionPane.showMessageDialog(null,"Error:"+e.getMessage(),"Error Occured",JOptionPane.ERROR_MESSAGE);
          System.out.println("Exeception at execQuery : datahandler"+e.getLocalizedMessage());
          return false;
      }



    }


    public static   boolean isBookAlready(Liste.Book book)
    {


        try {
            String check="select count(*) from issue where issueID=?";
            PreparedStatement preparedStatement=conn.prepareStatement(check);
            preparedStatement.setString(1,book.getId());
            ResultSet rs=preparedStatement.executeQuery();
            //int res=preparedStatement.executeUpdate();

            while(rs.next())
            {
                int count=rs.getInt(1);
                if(count>0)
                {
                    return true;
                }
                else {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;


    }



    public boolean updateBook(Liste.Book book)
    {

        try {
            String r="UPDATE book SET title=?,author=?,publisher=? WHERE id=?";

            PreparedStatement stm=conn.prepareStatement(r);
            stm.setString(1,book.getTitle());
            stm.setString(2,book.getAuthor());
            stm.setString(3,book.getPublisher());
            stm.setString(4,book.getId());
            int s=stm.executeUpdate();
            return (s>0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


public static boolean upadateMembre(ListeMembre.Membr membr)  {
    String query="UPDATE membre SET name=?,mobile=?,email=? WHERE membreId=?";
    PreparedStatement stm= null;
    try {
        stm = conn.prepareStatement(query);
        stm.setString(1,membr.getName());
        stm.setString(2,membr.getMobile());
        stm.setString(3,membr.getEmail());
        stm.setString(4,membr.getMembreid());
        int r=stm.executeUpdate();
        return (r>0);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;





}
}

/*
void setupBookTable()
{
    String TABLE_NAME="BOOK";
    try {
        statement = conn.createStatement();
        DatabaseMetaData dbm=conn.getMetaData();
        ResultSet tables=dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
        if(tables.next())
        {
            System.out.println("table "+TABLE_NAME+"already exists.Ready for go");
        }
        else
        {
            statement.execute("CREATE TABLE "+TABLE_NAME+"("
                    +"  id varchar(100) primary key,\n"
                    +"  title  varchar(200) ,\n"
                    +"  author varchar(200) ,\n"
                    +"  publisher varchar(100) ,\n"

                    +"  isAvail boolean default true,\n"




            );
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    */




import java.sql.*;
import java.sql.Statement;


public class testing {

    public static void main(String[] args) throws SQLException {
        Connection conn;
        String url="jdbc:mysql://localhost:3306/libary";
        String name="root";
        String pass="";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn= DriverManager.getConnection(url,name,pass);
            Statement statement=conn.createStatement();
            String requete="insert into book values ('b404', 'harry porter', 'gasmi', 'sid ahmad', '1');";

            //Selection operation
            ResultSet s=statement.executeQuery("SELECT * FROM book ");
            while(s.next())
            {
                System.out.println(s.getString("title"));
            }




          //  PreparedStatement preparedStmt = conn.prepareStatement(requete);
                     // preparedStmt.execute();
           // st.executeUpdate(requete);

              // ResultSet s=st.executeQuery(requete);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


}



package wajda9ni.dev.chefside;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MaConnection {


    public static Connection connect(){
        String monDriver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(monDriver);
            System.out.print("Driver Charged ... ");
        } catch (ClassNotFoundException e) {
            System.out.print("ERROR DRIVER");
        }
        //Connection a la base
        String url = "jdbc:mysql://127.0.0.1/tpjava";
        String user = "root";
        String mp = "";
        Connection con = null;


        try {
            con = DriverManager.getConnection(url, user, mp);
            System.out.print("Connected");
            return con;
        } catch (SQLException e) {
            System.out.print("ERROR : " + e.getMessage());
            return null ;
        }




    }
}

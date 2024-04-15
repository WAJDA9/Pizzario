package wajda9ni.dev.chefside;

import java.sql.*;

public class CommandeDAO {
    Connection con = null;
    Statement st = null;
    CommandeDAO(){
        con = MaConnection.connect();
        if(con != null){
            try {
                st = con.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public int insert(int id, String item, String type,String top, Double m){
        String req = "insert into Commande values(?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1,id);
            ps.setString(2,item);
            ps.setString(3,type);
            ps.setString(4,top);
            ps.setDouble(5,m);
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }
    }
    public  ResultSet select(String req){
        try {
            return st.executeQuery(req);
        } catch (SQLException e) {
            return null;
        }
    }
    public void showResultSet(ResultSet rs){
        if(rs!=null){
            while(true){
                try {
                    if (!rs.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                int c = 0;
                try {
                    c = rs.getInt(1);
                    System.out.print(c);
                } catch (SQLException e) {
                    System.out.print("ERROR");
                }

            }
        }
    }
    /*
    public int deleteResult(int id){

        String req = "delete from Etudiant where numcin = ?";
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1,id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            return 0;
        }

    }
    */

}

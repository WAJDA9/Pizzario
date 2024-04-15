package wajda9ni.dev.chefside;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class initTableThread extends Thread{
    ObservableList<Commande> data;
    TableView tableCommande;

    public initTableThread(ObservableList<Commande> data,TableView tableCommande){
        this.data =data;
        this.tableCommande = tableCommande;
    }



    public void run() {

        CommandeDAO dao = new CommandeDAO();
System.out.println("wsolt lahne 3");
        try {
            ResultSet rs = dao.select("select * from Commande");
            ResultSetMetaData rsmd = rs.getMetaData();
            int nbcol = rsmd.getColumnCount();

            for(int i=0;i<nbcol;i++){
                TableColumn tc = new TableColumn(rsmd.getColumnName(i + 1));
                tc.setCellValueFactory(new PropertyValueFactory<>(rsmd.getColumnName(i+1)));
                tableCommande.getColumns().add(tc);
            }
            System.out.println("wsolt lahne 4");
            while(rs.next()){
                int id = rs.getInt(1);
                String type = rs.getString(2);
                String item = rs.getString(3);
                String toppings = rs.getString(4);
                Double price = rs.getDouble(5);
                data.add(new Commande(id,type,item,toppings,price));
            }
            tableCommande.setItems(data);
            System.out.println("wsolt lahne 5");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

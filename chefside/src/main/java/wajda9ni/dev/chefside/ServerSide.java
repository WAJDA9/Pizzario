package wajda9ni.dev.chefside;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ServerSide extends Application {
    BorderPane bd = new BorderPane();
    TableView tableCommande = new TableView<Commande>();
    ObservableList<Commande> data = FXCollections.observableArrayList();
    Label lId, lType, lItem, lToppings,lPrice;


    @Override
    public void start(Stage stage) throws IOException {

        // UI FOR SERVER SIDE APP


        // Communication between Server and client
        System.out.println("LAUNCH SERVER ...");
        int PORT = 9001 ;
        int MAX_NUMBER = 40 ;
        int cpClients = 0 ;
        try {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("LISTENING ON "+PORT+" ...");
        while(cpClients < MAX_NUMBER){
            Socket s = server.accept();
            System.out.println("[CLIENT "+cpClients+" IS CONNECTED]");
            Discussion d = new Discussion(s,cpClients,data,tableCommande);
            cpClients++;
            System.out.println("wsolt lahne 1");

            d.start();

            System.out.println("aaaaaaa rasi");
            bd.setCenter(tableCommande);

        }
         } catch (IOException e) {
        throw new RuntimeException(e);
          }


        Scene scene = new Scene(bd, 320, 240);
        stage.setTitle("SERVER_SIDE");
        stage.setScene(scene);
        stage.show();


    }/*
    public void initTableCommande(){

        CommandeDAO dao = new CommandeDAO();

        try {
            ResultSet rs = dao.select("select * from Commande");
            ResultSetMetaData rsmd = rs.getMetaData();
            int nbcol = rsmd.getColumnCount();

            for(int i=0;i<nbcol;i++){
                TableColumn tc = new TableColumn(rsmd.getColumnName(i + 1));
                tc.setCellValueFactory(new PropertyValueFactory<>(rsmd.getColumnName(i+1)));
                tableCommande.getColumns().add(tc);
            }

            while(rs.next()){
                int id = rs.getInt(1);
                String type = rs.getString(2);
                String item = rs.getString(3);
                String toppings = rs.getString(4);
                Double price = rs.getDouble(5);
                data.add(new Commande(id,type,item,toppings,price));
            }
            tableCommande.setItems(data);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    */
    public static void main(String[] args) {
        launch();
    }
}
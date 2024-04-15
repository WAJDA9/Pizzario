package wajda9ni.dev.chefside;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;


public class Discussion extends Thread{
    Socket s;
    int id;
    TableView tableCommande = new TableView<Commande>();
    ObservableList<Commande> data = FXCollections.observableArrayList();


    public Discussion(Socket s,int id,ObservableList<Commande> data, TableView tableCommande) {
        this.s = s;
        this.id = id;
        this.data=data;
        this.tableCommande =tableCommande;

    }
    public void run(){
        try {
            PrintWriter pw = new PrintWriter(s.getOutputStream());
            pw.println("YOU ARE CONNECTED TO SERVER");
            pw.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader((s.getInputStream())));
            String line = br.readLine();
            System.out.println(line);
            String[] commandeString = line.split("-");
            CommandeDAO cmdDAO = new CommandeDAO();

            cmdDAO.insert(id,commandeString[1],commandeString[0],commandeString[2],2.2);
            cmdDAO.showResultSet(cmdDAO.select("select * from Commande"));
            System.out.println("wsolt lahne 2");
            new initTableThread(data,tableCommande).start();

           // new Lecture(br,id).start();
            // DATABASE CONNECTION



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

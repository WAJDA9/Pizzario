package wajda9.dev.pizzario;

import java.io.PrintWriter;
import java.util.Scanner;

public class Ecriture extends Thread{
    PrintWriter pw;
    String order ;
    public Ecriture(PrintWriter pw, String order) {
        this.pw = pw ;
        this.order = order ;
    }
    public void run(){

            pw.println(order);
            pw.flush();

    }
}

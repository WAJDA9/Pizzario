package wajda9ni.dev.chefside;

import java.io.BufferedReader;
import java.io.IOException;

public class Lecture extends Thread{
    BufferedReader br;
    int id;

    public Lecture(BufferedReader br,int id) {
        this.br = br;
        this.id = id;
    }
    public void run(){
        while (true){
            try {


                System.out.println(br.readLine().split("-")[0]);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

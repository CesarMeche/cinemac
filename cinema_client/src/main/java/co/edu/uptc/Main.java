package co.edu.uptc;

import java.io.IOException;

import co.edu.uptc.controller.Controller;
import co.edu.uptc.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Controller c= new Controller("localhost", 1235);
        try {
            c.connect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }
}
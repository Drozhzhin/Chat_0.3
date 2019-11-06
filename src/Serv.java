
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Serv {


    static ArrayList<String> messages;
    BufferedReader reader;
    BufferedWriter writer;
    String message;
    public static List<Socket> clients;
    public static final int PORT = 9000;
    public static ServerSocket server;



    public static void main(String[] args) {

        try {
            messages = new ArrayList<>();
            clients = new ArrayList<>();
            server = new ServerSocket(PORT);
            Socket client;
            System.out.println("Server is running...");

                while(true){
                    client = server.accept();
                    System.out.println(client + " is accepted.");
                    clients.add(client);
                    new SomeServer(client);
                }


        } catch (IOException e) {e.printStackTrace();}


    }



}


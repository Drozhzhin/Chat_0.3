import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Server {

    ServerSocket server;
    Socket client;
    BufferedReader reder;
    BufferedWriter writer;
    JTextArea area;
    JTextField field;
    String message;
    ArrayList<String> messages;
    public static List <Socket> clients;
    public static final int PORT = 9000;



        public Server(){
            new Form();

            try {
                server = new ServerSocket(PORT);
                System.out.println("Server is running!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            clients = new ArrayList<>();
            messages = new ArrayList<>();
            go();
        }


        public void go(){

            try {
                client = server.accept();
                new Sender();

                } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public class Sender implements Runnable{

            Thread th;

            public Sender(){
                th = new Thread();
                th.start();
                clients.add(client);
                System.out.println("Now " + clients.size() + " is connected");
                run();
            }


            @Override
            public void run() {

                try {
                    while (true) {
                        client = server.accept();
                        reder = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                        message = reder.readLine();
                        messages.add(message);
                        System.out.println(messages.size());

                        if(message.equals("hops")){
                            for (String message : messages){
                                System.out.println(client.getInetAddress() + " " + message);
                            }
                        }


                            area.append(message + "\n");
                            writer.write(message + "\n");
                            writer.newLine();
                            writer.flush();
                            writer.close();
                        }

                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        }


        public class Form{

            public Form(){
                JFrame frame = new JFrame("Server Dialog Window");
                frame.setBounds(120,120,450,590);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                JPanel panel = new JPanel();
                frame.add(panel);
                area = new JTextArea(30,30);
                panel.add(area);
                field = new JTextField(30);
                panel.add(field);
                JButton button = new JButton("Send");
                panel.add(button);

                    button.addActionListener(new SendButton());
            frame.setVisible(true);
            }

                           public class SendButton implements ActionListener{


                               @Override
                               public void actionPerformed(ActionEvent e) {
                                   try {
                                       writer.write(field.getText() + "\n");
                                       writer.newLine();
                                       writer.flush();


                                       field.setText("");
                                       field.grabFocus();


                                   } catch (IOException ex) {
                                       ex.printStackTrace();
                                   }


                               }
                           }


        }


    public static void main(String[] args) {
        new Server();

        }




}

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SomeServer implements Runnable {
    Socket client;
    BufferedReader reader;
    BufferedWriter writer;
    String message;

    Thread th;

            public SomeServer(Socket client){
             this.client = client;

                try {
                    reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }


             th = new Thread(this);
             th.start();

            }


    @Override
    public void run() {



            while (true){

                try {
                    message = reader.readLine();
                    Serv.messages.add(message);
                    System.out.println(message);
                    Serv.clients.remove(client);

                        if(message.equals("spot")){

                            for(Socket client: Serv.clients){
                                System.out.println(client);
                            }

                        }

                        for(Socket client : Serv.clients){
                            writer.write(message);
                            writer.flush();
                        }

                } catch (IOException e) {e.printStackTrace();}
            }

    }


}

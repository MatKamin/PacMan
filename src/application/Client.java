package application;


import java.io.*;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static application.Server.*;

public class Client extends Thread implements Serializable{

    static Socket connection;
    static ObjectInputStream in;
    static ObjectOutputStream out;

    public Client(Socket connection) throws IOException {
        Client.connection = connection;
        in = new ObjectInputStream(connection.getInputStream());
        out = new ObjectOutputStream(connection.getOutputStream());
        out.flush();

    }


    public void readReceivedScore() {

    }



    private ScheduledExecutorService executor2;
    private Runnable helloRunnable;

    @Override
    public void run() {
        System.out.println("Connected to " + connection.getRemoteSocketAddress());

        helloRunnable = () -> {
            try {
                if (in.readUTF().equals("Unknown Player,0")) return;

                clientsScoreMap.put(in.readUTF().split(",")[0], Integer.parseInt(in.readUTF().split(",")[1]));

                clientsScoreMap.forEach((key, value) -> {
                    if (key.equals("Unknown Player")) return;
                    try {
                        writeToAll(key + "," + value);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        };


        executor2 = Executors.newScheduledThreadPool(1);
        executor2.scheduleAtFixedRate(helloRunnable, 0, 3, TimeUnit.SECONDS);


    }

    private void write(String obj) throws IOException {
        out.writeUTF(obj);
        out.flush();
    }

    private void writeToAll(String obj) throws IOException {
        for (Client c : connections){
            c.write(obj);
        }
    }
}
package application;


import java.io.*;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static application.Server.*;

public class Client extends Thread {

    static Socket connection;
    static ObjectInputStream in;
    static ObjectOutputStream out;

    public Client(Socket connection) throws IOException {
        Client.connection = connection;
        in = new ObjectInputStream(connection.getInputStream());
        out = new ObjectOutputStream(connection.getOutputStream());
        out.flush();

        readReceivedScore();
    }


    public static void readReceivedScore() {
        Runnable helloRunnable = () -> {
            try {

                if (in.readUTF().equals("Unknown Player,0")) return;
                //writeToAll(in.readUTF());


                clientsScoreMap.put(in.readUTF().split(",")[0], Integer.parseInt(in.readUTF().split(",")[1]));


                clientsScoreMap.forEach((key, value) -> {

                    if (key.equals("Unknown Player")) return;
                    try {
                        writeToAll(key + "," + value);
                        //out.writeUTF(key + "," + value + "," + connections.size());
                        //out.reset();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                out.flush();



                //printScores();

            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(1);
        executor2.scheduleAtFixedRate(helloRunnable, 0, 5, TimeUnit.SECONDS);
    }




    @Override
    public void run() {
        System.out.println("Connected to " + connection.getRemoteSocketAddress());
    }

    public static void write(String obj) throws IOException {
        out.writeUTF(obj);
        out.flush();
    }

    public static void writeToAll(String obj) throws IOException {
        for (Client c : connections) {
            c.write(obj);
        }
    }
}
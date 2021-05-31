package application;


import java.io.*;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static application.Server.clientsScoreMap;
import static application.Server.connections;

public class Client extends Thread implements Serializable {

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
                clientsScoreMap.put(in.readObject().toString().split(",")[0], Integer.parseInt(in.readObject().toString().split(",")[1]));

                clientsScoreMap.forEach((key, value) -> {

                    if (key.equals("Unknown Player")) return;
                    try {
                        out.writeObject(key + "," + value + "," + connections.size());
                        out.reset();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                out.flush();



                //printScores();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        };

        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(1);
        executor2.scheduleAtFixedRate(helloRunnable, 0, 2, TimeUnit.SECONDS);
    }




    @Override
    public void run() {
        System.out.println("Connected to " + connection.getRemoteSocketAddress());
    }

    public static void write(Object obj) throws IOException {
        out.writeObject(obj);
        out.flush();
    }

    public static void writeToAll(Object obj) throws IOException {
        for (Client connection : connections) {
            connection.write(obj);
        }
    }

    public static int getConnectionCount() {
        return connections.size();
    }
}
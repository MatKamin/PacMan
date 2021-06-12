package application;


import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static application.Server.*;

public class Client extends Thread implements Serializable{

    Socket connection;
    ObjectInputStream in;
    ObjectOutputStream out;

    public Client(Socket connection) throws IOException {
        this.connection = connection;
        in = new ObjectInputStream(connection.getInputStream());
        out = new ObjectOutputStream(connection.getOutputStream());
        out.flush();

    }


    private ScheduledExecutorService executor2;
    private Runnable helloRunnable;

    int s = 0;
    @Override
    public void run() {
        System.out.println("Connected to " + connection.getRemoteSocketAddress());

        helloRunnable = () -> {
            try {

                clientsScoreMap.clear();
                clientsScoreMap.put(in.readUTF().split(",")[0], in.readUTF().split(",")[1]);

                writeToAll(clientsScoreMap.toString());

            } catch (EOFException | SocketException f) {
                System.out.println("No more data to read");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };


        executor2 = Executors.newScheduledThreadPool(1);
        executor2.scheduleAtFixedRate(helloRunnable, 1, 1, TimeUnit.SECONDS);


    }

    private void write(String obj) {
        try {
            out.writeUTF(obj);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeToAll(String obj) throws IOException {
        for (Client c : connections) {
            c.write(obj);
        }
    }
}
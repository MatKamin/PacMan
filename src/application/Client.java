package application;


import java.io.*;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static application.Server.clientsScoreMap;

public class Client extends Thread {

    Socket connection;
    static ObjectInputStream in;
    static ObjectOutputStream out;

    public Client(Socket connection) throws IOException {
        this.connection = connection;
        in = new ObjectInputStream(connection.getInputStream());
        out = new ObjectOutputStream(connection.getOutputStream());
    }

    public static void readReceivedScore() {
        Runnable helloRunnable = () -> {
            try {
                clientsScoreMap.put(read().toString().split(",")[0], Integer.parseInt(read().toString().split(",")[1]));
                System.out.println();
                printScores();

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

    static PrintWriter outPrint;

    static {
        try {
            outPrint = new PrintWriter("printWriter.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printScores() {
        outPrint.print("");
        outPrint.close();
        try (PrintWriter outPrint = new PrintWriter("printWriter.txt")) {
            clientsScoreMap.forEach((key, value) -> outPrint.println(key + "," + value));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        clientsScoreMap.forEach((key, value) -> System.out.println(key + "," + value));
    }

    public static Object read() throws IOException, ClassNotFoundException {
        return in.readObject();
    }
}
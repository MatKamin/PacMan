package application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    public static int PORT = 10024;
    public static ServerSocket serverSocket;
    public static boolean checkScore = true;
    public static HashMap<String, String> clientsScoreMap = new HashMap<>();
    static List<Client> connections = new ArrayList<Client>();   // Array List of all connections
    public static Socket verbindung;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server running on " + InetAddress.getLocalHost().getHostAddress() + " and Port " + PORT);

        Thread wait = new Thread(() -> {
            while (true) try {
                waitForClient(serverSocket.accept());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        wait.start();
    }
    

    public static void waitForClient(Socket connection) throws IOException {
        Client client = new Client(connection);
        connections.add(client);
        client.start();
    }
}

package application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int PORT = 10024;
    public static ServerSocket serverSocket;
    public static volatile boolean javaFxLaunched = false;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server running on " + InetAddress.getLocalHost() + " and Port " + PORT);
        Thread wait = new Thread(() -> {
            while (true) {
                try {
                    waitForClient(serverSocket.accept());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        wait.start();


    }

    public static void waitForClient(Socket connection) throws IOException{
        Client client = new Client(connection);
        client.start();
    }
}

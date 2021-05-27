package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {

    Socket connection;

    public Client(Socket connection) throws IOException{
        this.connection = connection;
    }

    @Override
    public void run() {
        System.out.println("Connected to " + connection.getRemoteSocketAddress());
        main.main(null);
    }
}
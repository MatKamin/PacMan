package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {

    Socket connection;
    ObjectInputStream in;
    ObjectOutputStream out;

    public Client(Socket connection) throws IOException, ClassNotFoundException {
        this.connection = connection;
        in = new ObjectInputStream(connection.getInputStream());
        out = new ObjectOutputStream(connection.getOutputStream());
        Object hallo = read();
        System.out.println(hallo);
    }

    @Override
    public void run() {
        System.out.println("Connected to " + connection.getRemoteSocketAddress());
        main.myLaunch(main.class);
    }

    public void write(Object obj) throws IOException {
        out.writeObject(obj);
    }

    public Object read() throws IOException, ClassNotFoundException {
        return in.readObject();
    }
}
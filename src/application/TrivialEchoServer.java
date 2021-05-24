package application;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static application.TrivialEchoServer.ClientNicknames;
import static application.TrivialEchoServer.connections;
import static application.main.myLaunch;

public class TrivialEchoServer {

    public static int PORT = 10023;
    public static int ECHOS = 3;
    public static boolean[] isRunning = new boolean[10]; // Allow only 10 connections
    public static List<Client> connections = new ArrayList<Client>();   // Array List of all connections
    public static String[] ClientNicknames = new String[100];           // Array with all Client Nicknames, Max 100 clients


    public static void main(String[] args) {
        // Server auf Port 10023 horchen lassen
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Echo-Server bereit am Port " + PORT);
            while (true) {
                try (
                        // Auf ankommende Verbindung warten, accept() blockiert so lange
                        Socket verbindung = server.accept();
                        // TODO: ab hier sollte der Rest der while-Schleife in einen eigenen
                        // Client-Thread (z.B. EchoHandler.java) ausgelagert werden.
                        Writer w = new OutputStreamWriter(verbindung.getOutputStream(),
                                StandardCharsets.ISO_8859_1);
                        BufferedWriter bw = new BufferedWriter(w);
                        Reader r = new InputStreamReader(verbindung.getInputStream());
                        BufferedReader br = new BufferedReader(r)
                ) {

                    System.out.println("Verbindung angenommen von " +
                            verbindung.getRemoteSocketAddress());
                    while (true) {

                        myLaunch(main.class);


                        // Eingabeaufforderung senden und Zeile einlesen
                        bw.write("WILLKOMMEN:\r\n");
                        bw.flush();

                        break;
                    }

                    System.out.println("Verbindung beendet mit " + verbindung.getRemoteSocketAddress());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




/**
 * Client Class manages Clients
 */
class Client extends Thread {
    private static Socket socket;
    private PrintWriter outputWriter;
    private BufferedReader inputReader;
    private int counter;
    public static int nextClient = 0;
    public static int currentClient;

    /**
     * Client Constructor
     * @param socket Socket
     * @param i      ClientCounter
     */
    public Client(Socket socket, int i) {
        Client.socket = socket;
        this.counter = i;
        try {
            inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * sends message
     * @param message message to send
     */
    protected void sendMessage(String message) {
        outputWriter.println(message);
    }


    /**
     * run Method
     */
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String ClientText;

            sendMessage("WELCOME!");
            sendMessage("Enter 'quit' to quit");


            int currentClient = nextClient;
            Client.currentClient = currentClient;
            nextClient++;


            do {
                outputWriter.flush();
                ClientText = reader.readLine();

                if(ClientText.equals("quit")){
                    connections.get(currentClient).interrupt();
                    connections.remove(currentClient);

                    for (int i = currentClient; i < ClientNicknames.length - 1; i++) {
                        ClientNicknames[i] = ClientNicknames[i + 1];
                    }

                }

            } while (!ClientText.equals("quit"));

            //socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
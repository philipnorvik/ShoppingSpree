package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerListener {

    private int port = 4200;
    private Socket connection;
    private ServerSocket serverSocket;

    private ServerListener() {

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Could not set up ServerSocket...");
        }

        while (true) {

            try {
                System.out.println("Waiting for clients to connect...");

                connection = serverSocket.accept();

                Player p1 = new Player(connection);
                System.out.println("Connected to player 1: " +
                        connection.getInetAddress().getHostName());

                Player p2 = new Player(serverSocket.accept());
                System.out.println("Connected to player 2: " +
                        connection.getInetAddress().getHostName());

                System.out.println("Starting game...");

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        new PlayerHandler(p1, p2);
                    }

                }).start();

            } catch (IOException e) {
                System.out.println("Lost connection to client(s)...");
            }
        }
    }

    public static void main(String[] args) {

        new ServerListener();
    }
}

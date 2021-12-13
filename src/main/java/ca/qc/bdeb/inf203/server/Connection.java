package ca.qc.bdeb.inf203.server;

import ca.qc.bdeb.inf203.shared.Message;
import ca.qc.bdeb.inf203.shared.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class Connection {
    private Socket clientSocket;
    private PrintWriter socketOutput;
    private BufferedReader donneesRecues;
    private boolean connectionOpen;

    public Connection(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        socketOutput = new PrintWriter(clientSocket.getOutputStream(), true);
        donneesRecues = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        connectionOpen = true;
    }

    public void close() throws IOException {
        clientSocket.close();
        socketOutput.close();
        donneesRecues.close();
        connectionOpen = false;
    }

    public Message readMessage() {
        if (connectionOpen) {
            try {
                String[] msg = donneesRecues.readLine().split(Message.separator);
                return new Message(new User(msg[0], msg[1]), msg[2]);
            } catch (IOException e) {
                System.out.println("An error occurred while ");
                return new Message(null, "");
            }
        } else {
            return null;
        }
    }

    public void send(String message) {
        if (connectionOpen) {
            Message message1 = new Message(Server.ServerUser, message);
            socketOutput.println(message1.getFormattedMsg());
        }
    }

    public void talkWithClient() {
        // Tant que le client envoie des données, on les affiche
        Thread tr = new Thread(() -> {
            Message ligne;

            while ((ligne = readMessage()) != null) {
                System.out.println("Reçu: " + ligne);
                send("Reçu! " + new Date());

                if (ligne.getMessage().equals("exit")) {
                    try {
                        close();
                    } catch (IOException e) {
                        System.out.println("An error occurred while disconnecting a user!");
                    }
                }
            }
            System.out.println("A user disconnected");
        });

        tr.start();
    }

    public boolean isConnectionOpen() {
        return connectionOpen;
    }

    public void setConnectionOpen(boolean connectionOpen) {
        this.connectionOpen = connectionOpen;
    }
}

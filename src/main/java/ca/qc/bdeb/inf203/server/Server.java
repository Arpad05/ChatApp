package ca.qc.bdeb.inf203.server;

import ca.qc.bdeb.inf203.shared.Message;
import ca.qc.bdeb.inf203.shared.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private final int serverPort = 80, BACKLOG = 100;
    public final static User ServerUser = new User("Server", 0);
    private ServerSocket socket;
    private final ArrayList<Connection> userList;
    private final ArrayList<Message> messages;

    public Server() {
        try {
            socket = new ServerSocket(serverPort, BACKLOG);
        } catch (IOException e) {
            System.out.println("An error occurred while creating the server");
        }

        userList = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public void openSocket(){
        try {
            socket = new ServerSocket(serverPort, BACKLOG);
        } catch (IOException e) {
            System.out.println("An error occurred while opening the socket");
        }
    }


    public void closeServer() {
        try {
            for (Connection userSocket : userList)
                userSocket.close();

            socket.close();
        } catch (IOException e) {
            System.out.println("A error occurred while closing the connections!");
        }
    }

    public void addSocket() {
        try {
            userList.add(new Connection(socket.accept()));
        } catch (IOException e) {
            System.out.println("An error occurred while accepting a new connection.");
        }
    }

    public Connection getLastConnection() {
        return userList.get(userList.size() - 1);
    }

    public ArrayList<Connection> getUserList() {
        return userList;
    }

    public void addMessage(Message m){
        synchronized (messages){
            messages.add(m);
        }
    }

    public ArrayList<Message> getMessages(){
        synchronized (messages){
            return messages;
        }
    }
}

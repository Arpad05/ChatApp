package ca.qc.bdeb.inf203.client;

import ca.qc.bdeb.inf203.shared.Message;
import ca.qc.bdeb.inf203.shared.User;

import java.io.*;
import java.net.Socket;

public class Client {
    private final String serverIP = "127.0.0.1";
    private final int serverPort = 80;
    private Socket clientSocket;
    private BufferedReader readerListener;
    private BufferedReader console;
    private PrintWriter writer;
    private User user;
    private boolean clientConnected;
    private boolean consoleOpen;


    public Client(String name) {
        try {
            user = new User(name);
            clientSocket = new Socket(serverIP, serverPort);
            connect();
            listen();
            getConsoleReader();
            clientConnected = true;
        } catch (IOException e) {
            System.out.println("An error occurred during the initialisation of the client. Try to turn on the server first?");
        }
    }


    public void listen() {
        try {
            readerListener = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            System.out.println("A error occurred while trying to listen for the server!");
        }
    }

    public void stopListening() {
        try {
            readerListener.close();
        } catch (IOException e) {
            System.out.println("A error occurred while trying to stop listening for the server.");
        }
    }


    public void connect() {
        try {
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
        } catch (IOException e) {
            System.out.println("A error occurred while trying to connect to the server!");
        }
    }

    public void disconnect() {
        writer.close();
    }

    public void getConsoleReader() {
        console = new BufferedReader(new InputStreamReader(System.in));
        consoleOpen = true;
    }

    public void closeConnection() {
        if (isClientConnected()) {
            try {
                clientSocket.close();
                writer.close();
                readerListener.close();
                console.close();
                clientConnected = false;
                consoleOpen = false;
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

    public void send(String m) {
        if (m == null || m.equals(""))
            m = "exit";
        Message msg = new Message(user, m);
        writer.println(msg.getFormattedMsg());
    }

    public String getConsoleLine() {
        if (isConsoleOpen()) {
            try {
                return console.readLine();
            } catch (IOException e) {
                System.out.println("A error occurred while trying to get input from the console!");
                return "";
            }
        } else {
            return null;
        }
    }

    public void closeConsole() {
        try {
            console.close();
            setConsoleOpen(false);
        } catch (IOException e) {
            System.out.println("An error occurred while trying to close the console!");
        }
    }

    public Message receiveData() {
        try {
            String[] msg = readerListener.readLine().split(Message.separator);
            return new Message(new User(msg[0], msg[1]), msg[2]);
        } catch (IOException e) {
            System.out.println("A error occurred while listening to data stream from server.");
            return new Message(null, "");
        }
    }

    public String getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public BufferedReader getReaderListener() {
        return readerListener;
    }

    public void setReaderListener(BufferedReader readerListener) {
        this.readerListener = readerListener;
    }

    public BufferedReader getConsole() {
        return console;
    }

    public void setConsole(BufferedReader console) {
        this.console = console;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isClientConnected() {
        return clientConnected;
    }

    public void setClientConnected(boolean clientConnected) {
        this.clientConnected = clientConnected;
    }

    public boolean isConsoleOpen() {
        return consoleOpen;
    }

    public void setConsoleOpen(boolean consoleOpen) {
        this.consoleOpen = consoleOpen;
    }
}

package ca.qc.bdeb.inf203.server;

public class ServerLauncher {
    public static void main(String[] args) {
        //Start the server
        System.out.println("Starting server....");
        Server server = new Server();
        System.out.println("Server started!");

        do {
            server.addSocket();

            Connection connection = server.getLastConnection();

            connection.talkWithClient();

        } while (server.getUserList().size() > 0);

        server.closeServer();
    }
}

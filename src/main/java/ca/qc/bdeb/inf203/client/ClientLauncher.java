package ca.qc.bdeb.inf203.client;


public class ClientLauncher {
    public static void main(String[] args) {
        Client client = new Client("Alice");

        if (client.isClientConnected()) {
            String ligne;
            System.out.print("\n> ");
            while ((ligne = client.getConsoleLine()) != null) {
                // Envoi d’un message
                System.out.println("Envoi de : " + ligne);
                client.send(ligne);

                // Accusé de réception
                System.out.println(client.receiveData());
                System.out.print("> ");

                if (ligne.equals("exit")) {
                    System.out.println("Exiting......");
                    client.closeConsole();
                }
            }
        } else {
            System.out.println("Could not connect to server!");
        }

        client.closeConnection();
    }
}

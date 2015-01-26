package transmission;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

import classes.Generation;

public class MTServer {

	private static ServerSocket serverSocket; // Definisco un socket server (per ascoltare dati)
    private static Socket clientSocket = null; // Definisco un socket client a cui associerò il serverSocket (per trasmettere dati)

    public static void serverStart(int port){
	
    	try {
            serverSocket = new ServerSocket(port); // Apro un socket Server sulla porta port per ascoltare un client in arrivo
            System.out.println("Server started.");
        } catch (Exception e) {
            System.err.println("Port already in use.");
            System.exit(1);
        }
    	
    	while (true) {
            try {
                clientSocket = serverSocket.accept(); //appena un client mi contatta gli associo il socket Server e accetto la connessione
                System.out.println("Accepted connection : " + clientSocket);

                Thread t = new Thread(new CLIENTHandler(clientSocket)); //Creo un nuovo thread per gestire la singola chiamata client appena ricevuta

                t.start(); //faccio partire la run del thread indicata in CLIENTHandler

            } catch (Exception e) {
                System.err.println("Error in connection attempt.");
            }
        
           	
    	
    	}//fine while
    	
	
    }//fine serverStart
}//fine MyFileServer
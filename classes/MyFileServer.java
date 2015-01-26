package classes;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class MyFileServer {

	private static ServerSocket serverSocket;
    private static Socket clientSocket = null;

    public static void serverStart(int port){
	
    	try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started.");
        } catch (Exception e) {
            System.err.println("Port already in use.");
            System.exit(1);
        }
    	
    	while (true) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted connection : " + clientSocket);

                Thread t = new Thread(new MyCLIENTConnection(clientSocket));

                t.start();

            } catch (Exception e) {
                System.err.println("Error in connection attempt.");
            }
        }//fine while
    	
	
    }//fine serverStart
}//fine MyFileServer

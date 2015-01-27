package transmission;

import java.net.ServerSocket;
import java.net.Socket;

public class MTServer {

	private static ServerSocket serverSocket; // defining a server socket to listen data
    private static Socket clientSocket = null; // defining a client socket to send data

    public static void serverStart(int port){
    	int i=0;
    	try {
            serverSocket = new ServerSocket(port); // Opening a server socket to listen for client calls
            System.out.println("Server started.");
        } catch (Exception e) {
            System.err.println("Port already in use.");
            System.exit(1);
        }
    	
    	while (true) {
            
    		try {
            	
                clientSocket = serverSocket.accept(); //binding server socket to client socket incoming call and accepting call
                System.out.println("Accepted connection : " + clientSocket);
                i=i+1;
                Thread t = new Thread(new CLIENTHandler(clientSocket),"thread"+i); //Create a new thread to handle the single call coming from one client
                System.out.println("Thread "+t.getName()+" is starting");
                t.start(); //Starting the run method contained in CLIENTHandler class

            } catch (Exception e) {
                System.err.println("Error in connection attempt.");
            }
        
           	
    	
    	}//end while
    	
	
    }//end serverStart
}//end MyFileServer
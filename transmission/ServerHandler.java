package transmission;

import java.net.ServerSocket;
import java.net.Socket;

import resources.Structures.*;
import paillierp.key.PaillierPrivateKey;
import paillierp.key.PaillierPrivateThresholdKey;

public class ServerHandler implements Runnable {
	
	 private PaillierPrivateKey PrivKey;
	 private PPTKverify pv;
	 private SSverify sv;
	 private PDMcombine pdmc;
	 ServerSocket serverSocket=null; // defining a server socket to listen data
     Socket clientSocket = null; // defining a client socket to send data
	final int port=8080; 
 	int i=0;
	
	public ServerHandler(PaillierPrivateKey PR, PPTKverify pv, SSverify sv, PDMcombine pdmc) {
        
        this.PrivKey = PR;
        this.pv = pv;
        this.sv=sv;
        this.pdmc=pdmc;
        
    }
	
	public void run(){

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
            Thread t = new Thread(new newClientHandler(clientSocket, PrivKey, pv,sv,pdmc),"thread"+i); //Create a new thread to handle the single call coming from one client
            System.out.println("Thread "+t.getName()+" is starting");
            t.start(); //Starting the run method contained in newCLIENTHandler class
            
        } catch (Exception e) {
            System.err.println("Error in connection attempt.");
        }
    
		

	
	}//end while

}
}

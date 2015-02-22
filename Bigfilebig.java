import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.security.SecureRandom;

import paillierp.key.PaillierPrivateKey;
import paillierp.key.PaillierPrivateThresholdKey;
import resources.Generation;
import transmission.newClientHandler;


public class Bigfilebig {
	

	public static void main(String[] args) throws IOException {
		
		int n=5;
		PaillierPrivateKey[] NodePRs = new PaillierPrivateKey[5];
		PaillierPrivateThresholdKey PPTK = null;
		
		for (int i=0;i<n;i++){
			NodePRs[i]=Generation.retrieveMyPrivateKey((i+1), "chiavi statiche");
		}
		System.out.println("Loaded passwords");
		
		 ServerSocket serverSocket=null; // defining a server socket to listen data
	     Socket clientSocket = null; // defining a client socket to send data
		final int port=8080; 
	 	int i=0;
		try {
	        serverSocket = new ServerSocket(port); // Opening a server socket to listen for client calls
	        System.out.println("Server started.");
	    } catch (Exception e) {
	        System.err.println("Port already in use.");
	        System.exit(1);
	    }
		
		while (true) {
			
			 if(PPTK==null)
	            	System.out.println("Pptk still not received");
	            else
	            	System.out.println("Pptk received");
	        
			try {
	        	
	            clientSocket = serverSocket.accept(); //binding server socket to client socket incoming call and accepting call
	            System.out.println("Accepted connection : " + clientSocket);
	            i=i+1;
	            Thread t = new Thread(new newClientHandler(clientSocket, NodePRs[1], PPTK),"thread"+i); //Create a new thread to handle the single call coming from one client
	            System.out.println("Thread "+t.getName()+" is starting");
	            t.start(); //Starting the run method contained in newCLIENTHandler class

	           
				
				
	            
	        } catch (Exception e) {
	            System.err.println("Error in connection attempt.");
	        }
	    

		
		}//end while
	}

}

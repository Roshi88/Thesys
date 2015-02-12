import paillierp.key.PaillierPrivateKey;
import paillierp.Paillier;
import resources.Generation;
import resources.Utilities;
import transmission.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;



public class testinoPlus {

	public static void main(String[] args) throws IOException {
		
		int n=5; // numero di nodi della MANET
		
		PaillierPrivateKey[] MyNodesPrs = new PaillierPrivateKey[n];
		for (int i=0;i<n;i++){
			MyNodesPrs[i]=Generation.coupleGen((i+1), 32);
			while(MyNodesPrs[i].getN().toByteArray().length!=8)
				MyNodesPrs[i]=Generation.coupleGen((i+1), 32);
		}
				
		Generation.shareGen(n, 32); //genero i 5 file contenti gli shares 
		
		String[] chiavi = new String[n];
		String[] keys = new String[n];
		
		for (int i=0; i<n; i++){
			chiavi[i] = "chiave" + (i+1);
			keys[i] = "Tkey" + (i+1);
			Utilities.insertTemplateNum(chiavi[i],keys[i] , 1);
		}
		
		//Misuro lunghezza file chiavi
		
		long[] dims = new long[5];
		

		for (int i=0;i<n;i++){
			File tmp= new File(keys[i]);
			dims[i]=tmp.length();
			System.out.println(dims[i]);
		}
		
		final double MAX=202;
		
		double chunk_size=7;
		double a=MAX/chunk_size;
		System.out.println("Number of chunks: "+a);
		int num_of_chks=(int)Math.ceil(a);
		System.out.println("Rounded Number of chunks: "+num_of_chks);
		
		
		for (int i=0;i<n;i++){
		Utilities.splitFile(new File(keys[i]),(int)chunk_size);//divido il file in "num_of_chks" files di dimensione "chunk_size"
		}
		
		BigInteger[][] B= new BigInteger[n][num_of_chks];
		
		
		for (int j=0;j<n;j++){
		
			for (int i=0;i<num_of_chks;i++){
			
				if(i<9){
					B[j][i]=Utilities.fileToBigInteger("Tkey"+(j+1)+".00"+(i+1));
					}
				if(i>8){
					B[j][i]=Utilities.fileToBigInteger("Tkey"+(j+1)+".0"+(i+1));
					}
			}
		}
		
		Paillier esys = new Paillier();
		
		
		BigInteger[][] C = new BigInteger[n][num_of_chks];
		
		for (int j=0;j<n;j++){
			
			esys.setEncryption(MyNodesPrs[j].getPublicKey());
			
			
		for (int i=0;i<num_of_chks;i++){
			
			C[j][i]=esys.encrypt(B[j][i]);
						
			String name= new String();
			if(i<9){
				name = "Cyph"+(j+1)+".00"+(i+1);
			}
			if(i>8){
				name = "Cyph"+(j+1)+".0"+(i+1);
			}
			Utilities.newBigIntegerToFile(C[j][i], name); //ho il BigInt in un file
		}
		}
	
		
		
		Utilities.retrieveShare(MyNodesPrs[2], 3);
		
		
		
////////////////////////////////////////////////////////////////////////////////////////////////////
		
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
	        
			try {
	        	
	            clientSocket = serverSocket.accept(); //binding server socket to client socket incoming call and accepting call
	            System.out.println("Accepted connection : " + clientSocket);
	            i=i+1;
	            Thread t = new Thread(new newClientHandler(clientSocket,MyNodesPrs[0]),"thread"+i); //Create a new thread to handle the single call coming from one client
	            System.out.println("Thread "+t.getName()+" is starting");
	            t.start(); //Starting the run method contained in CLIENTHandler class

	        } catch (Exception e) {
	            System.err.println("Error in connection attempt.");
	        }
	    
	       	
		
		}//end while
		
		
		
		
		
		
	}//fine main

}//fine class testino

import paillierp.Paillier;
import paillierp.key.*;
import resources.Generation;
import resources.Utilities;
import transmission.MTMultiClient;
import transmission.newClientHandler;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class NewDaemon {

	public static void main(String[] args) {
		
		
		int n=5;
		BigInteger[] shares = new BigInteger[n];
		int[] sharesLng = new int[n];
		int[] nLng = new int[n];
		int size_of_cnk=7;
		int num_of_cnks=29;
		
/////////////////////////////////LOADING MANET PUBLIC KEYS///////////////////////////////////////
		
		PaillierPrivateKey[] NodePRs = new PaillierPrivateKey[5];
		
		for (int i=0;i<n;i++){
			NodePRs[i]=Generation.retrieveMyPrivateKey((i+1), "chiavi statiche");
			nLng[i] = NodePRs[i].getN().toByteArray().length;
			System.out.println(nLng[i]);
		}

/////////////////////////////////////GENERATING SHARES//////////////////////////////////////////
		String[] chiavi = new String[n];
		long[] dims = new long[5];
		boolean token=false;
		
		try{
			while(true){
			Generation.shareGen(n, 32);
			
			for (int i=0; i<n; i++){
				chiavi[i] = "chiave" + (i+1);
				File tmp= new File(chiavi[i]);
				dims[i]=tmp.length(); 	//I FILE BUONI SONO DA 197 A 203 BYTE
				if(dims[i]>203 || dims[i]<197){
					token=false;
					break;
				}else token=true;
				
				
			}
			if(token==true)
				break;
			}
			System.out.println(dims[0]+"\n"+dims[1]+"\n"+dims[2]+"\n"+dims[3]+"\n"+dims[4]);
			
		}catch(IOException e){
			System.out.println(e);
		}
		
////////////////////////SPLITTING SHARES FILES INTO CHUNKS OF DIMENSION 7 BYTES ////////////////
		
		for (int i=0;i<n;i++){
			try{
			Utilities.splitFile(new File(chiavi[i]),size_of_cnk);//divido il file in "num_of_chks" files di dimensione "chunk_size"
			}catch(IOException e){
				System.out.println(e);
			}
		}
		
////////////////////// PUTTING FILES INTO BIGINTEGERS IN AN 5x29 MATRIX ///////////////////////
		
BigInteger[][] B= new BigInteger[n][num_of_cnks];
BigInteger[][] C = new BigInteger[n][num_of_cnks];
Paillier esys = new Paillier();
		
		
		for (int j=0;j<n;j++){
		
			for (int i=0;i<num_of_cnks;i++){
			
				if(i<9){
					B[j][i]=Utilities.fileToBigInteger("chiave"+(j+1)+".00"+(i+1));
					}
				if(i>8){
					B[j][i]=Utilities.fileToBigInteger("chiave"+(j+1)+".0"+(i+1));
					}
			}
		}
		
		for (int j=0;j<n;j++){
			
			esys.setEncryption(NodePRs[j].getPublicKey());
			
			
			for (int i=0;i<num_of_cnks;i++){
			
				C[j][i]=esys.encrypt(B[j][i]);
						
			}
		}
		
		
		BigInteger[] shareTo2= C[1];
		BigInteger[] shareTo3= C[2];
		BigInteger[] shareTo4= C[3];
		BigInteger[] shareTo5= C[4];
		
		BigInteger[] plain = new BigInteger[num_of_cnks];
		 Paillier dsys = new Paillier();
		 dsys.setDecryptEncrypt(NodePRs[1]);
		 String tmp = new String();
		for (int i=0;i<num_of_cnks;i++){
			 plain[i]=dsys.decrypt(shareTo2[i]); //sistemare qua, devo cominciare a mandare i veri biginteger criptati
			 tmp=tmp+plain[i].toString();
			 }
			 
			 BigInteger plainMsg= new BigInteger(tmp);
			 
			 System.out.println(plainMsg);
			 Utilities.newBigIntegerToFile(plainMsg, "recovered");
			 
		
		System.out.println("Array length: "+shareTo2.length);
		
		BigInteger Preamble = Utilities.stringToBigInteger("1-"+num_of_cnks);
		//MTMultiClient.bigintTransmit(8080, "localhost", shareTo2, Preamble);
		
		
////////////////////////////////STARTING THE RECEIVING SIDE//////////////////////////////////////////////
		
//		 ServerSocket serverSocket=null; // defining a server socket to listen data
//	     Socket clientSocket = null; // defining a client socket to send data
//		
//	    final int port=8080; 
//	 	int i=0;
//		try {
//	        serverSocket = new ServerSocket(port); // Opening a server socket to listen for client calls
//	        System.out.println("Server started.");
//	    } catch (Exception e) {
//	        System.err.println("Port already in use.");
//	        System.exit(1);
//	    }
//		
//		while (true) {
//	        
//			try {
//	        	
//	            clientSocket = serverSocket.accept(); //binding server socket to client socket incoming call and accepting call
//	            System.out.println("Accepted connection : " + clientSocket);
//	            i=i+1;
//	            Thread t = new Thread(new newClientHandler(clientSocket, NodePRs[1]),"thread"+i); //Create a new thread to handle the single call coming from one client
//	            System.out.println("Thread "+t.getName()+" is starting");
//	            t.start(); //Starting the run method contained in CLIENTHandler class
//
//	        } catch (Exception e) {
//	            System.err.println("Error in connection attempt.");
//	        }
//	    
//	       	
//		
//		}//end while
		
		
		
		
	}//main end

}//newdaemon end

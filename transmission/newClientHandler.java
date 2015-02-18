package transmission;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;
import java.math.BigInteger;

import paillierp.Paillier;
import paillierp.key.PaillierPrivateKey;
import resources.Utilities;

public class newClientHandler implements Runnable {

	 
	 private final static int FILE_SIZE=6022386;
	 
	
	 private Socket clientSocket;
	 private PaillierPrivateKey PrivKey;
	 ServerSocket servSock;
	 BigInteger[] msg = null;
	 BigInteger preamble = null;
	 int bytesRead;
	 int current = 0;
	 DataOutputStream dos = null;
	 BufferedReader dis = null;
	 FileOutputStream fos = null;
	 BufferedOutputStream bos = null;
	 int msgtype=-1;
	 int num_of_rx_cnks=-1;
	 
	 
	public newClientHandler(Socket client, PaillierPrivateKey PR) {
        this.clientSocket = client;
        this.PrivKey = PR;
        
    }
	
//I CAN RECEIVE 3 TYPES OF MESSAGES: SHARE, THE ENCRYPTED PASSWORD, THE 4 PDMS
	public void run() {
		try{
			
			 ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
			 ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			 preamble = (BigInteger) ois.readObject();
			 
			 
			 System.out.println("Received Preamble is:"+preamble);
			 oos.writeObject("Received preamble");
			 msg =(BigInteger[]) ois.readObject();
			 System.out.println("Received Message is:"+msg+"\n"+msg[0]+"\n"+msg[2]);
			 
			 

			 String sPlain = Utilities.bigIntegerToString(preamble);
			 String[] splitArr=Pattern.compile("-").split(sPlain);
			 msgtype=Integer.parseInt(splitArr[0]);
			 num_of_rx_cnks=Integer.parseInt(splitArr[1]);
			 System.out.println("Message type: "+msgtype+"\n"+"Number of received cnks: "+num_of_rx_cnks);
			 
			 Paillier dsys = new Paillier();
			 dsys.setDecryptEncrypt(PrivKey);
			 String tmp = null;
			 BigInteger[] plain = new BigInteger[num_of_rx_cnks];
			 for (int i=0;i<num_of_rx_cnks;i++){
			 plain[i]=dsys.decrypt(msg[i]); //sistemare qua, devo cominciare a mandare i veri biginteger criptati
			 tmp=tmp+plain[i].toString();
			 }
			 
			 BigInteger plainMsg= new BigInteger(tmp);
			 
			 System.out.println(plainMsg);
			 //a questo punto ho i miei 29 biginteger. Li devo sistemare uno accanto all'altro e rimettere nel file. 
			 
			 
			
			 switch(msgtype){
			 
			 case 1: //Share received
				 
				 System.out.println("Received the share");
				 
				 break;
				 
			 case 2: // Session Secret received
				 
				 break;
				 
			 case 3: //PDM received
				 
				 break;
			 
			 
			 }//end switch
			 



		
		}catch(IOException ioe){
			System.out.println(ioe);
		}catch(ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}finally {
	    	try{
		          if (dis != null) dis.close();
		          if (dos != null) dos.close();
		          if (clientSocket!=null) clientSocket.close();
		    	}catch (IOException e){
		    		System.out.println(e);
		    		}
		    }
          
        	
        	
        }
}


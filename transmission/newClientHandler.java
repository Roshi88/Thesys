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
	 BigInteger plain = null;
	 int bytesRead;
	 int current = 0;
	 DataOutputStream dos = null;
	 BufferedReader dis = null;
	 FileOutputStream fos = null;
	 BufferedOutputStream bos = null;
	 int msgtype=-1;
	 int num_of_rx_cnks=-1;
	 
	 
	public newClientHandler(Socket client) {
        this.clientSocket = client;
        
    }
	
//I CAN RECEIVE 3 TYPES OF MESSAGES: SHARE, THE ENCRYPTED PASSWORD, THE 4 PDMS
	public void run() {
		try{
			
			 ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
			 ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			 Object obj = ois.readObject();
			 plain=Utilities.getBigInteger(obj);
			 System.out.println("Received Biginteger is:"+plain);
			 oos.writeObject("Received");
			 String sPlain = Utilities.bigIntegerToString(plain);
			 String[] splitArr=Pattern.compile("-").split(sPlain);
			 msgtype=Integer.parseInt(splitArr[0]);
			 num_of_rx_cnks=Integer.parseInt(splitArr[1]);
			 if(msgtype==1)
				 System.out.println("Message Type= 1");
			 
//			 Paillier dsys = new Paillier();
//			 dsys.setDecryptEncrypt(PrivKey);
//			 plain=dsys.decrypt(msg);
//			 String sMsg = Utilities.bigIntegerToString(plain);
//			 
//			 String[] splitArr=Pattern.compile("-").split(sMsg);
//			 System.out.println(splitArr[0]);
//			 System.out.println(splitArr[1]);

		
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


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
	 int num_of_chunks=-1;
	 
	 
	public newClientHandler(Socket client) {
        this.clientSocket = client;
        
    }
	
	public void run() {
		try{
			
			 ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
			 ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			 msg = (BigInteger[]) ois.readObject();
			 System.out.println("Received Biginteger is:"+msg);
			 oos.writeObject("Received");
			 System.out.println(msg[0]+","+msg[1]+","+msg[2]);
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


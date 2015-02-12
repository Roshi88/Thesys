package transmission;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.UnknownHostException;


public class ProvaClient {

	public static void main(String[] args) {
		
		
	    Socket clientSock = null;
	    ServerSocket serverSock = null;
	   
	    String ipv4="localhost";
	    int port = 8080;
	    
	    try {
	      clientSock = new Socket(ipv4, port);
	      System.out.println("Connecting...");
	      
	      
	      
	      
	      
	    }catch(UnknownHostException uhe){
	    	System.out.println(uhe);
	    }catch(IOException ioe){
	    	System.out.println(ioe);
	    }
	}

}

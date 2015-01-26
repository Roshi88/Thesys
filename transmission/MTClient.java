package transmission;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MTClient {		
		
		public static void fileTransmit(int port, String ipv4, String pathName){
			
			int bytesRead;
		    int current = 0;
		    FileInputStream fis = null;
		    BufferedInputStream bis = null;
		    Socket clientSock = null;
		    OutputStream os = null;
		    
		    try {
		      clientSock = new Socket(ipv4, port);
		      System.out.println("Connecting...");
		      File myFile = new File (pathName);
	          byte [] mybytearray  = new byte [(int)myFile.length()];
	          fis = new FileInputStream(myFile);
	          bis = new BufferedInputStream(fis);
	          bis.read(mybytearray,0,mybytearray.length);
	          os = clientSock.getOutputStream();
	          System.out.println("Sending " + pathName + "(" + mybytearray.length + " bytes)");
	          os.write(mybytearray,0,mybytearray.length);
	          os.flush();
	          System.out.println("Done.");
		    
		    
		    }catch(IOException e){
				System.out.println(e);
			}
		    finally {
		    	try{
		          if (bis != null) bis.close();
		          if (os != null) os.close();
		          if (clientSock!=null) clientSock.close();
		    	}catch (IOException e){
		    		System.out.println(e);
		    		}
		    }
		}	
		
}
		



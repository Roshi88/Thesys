package transmission;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.math.BigInteger;
import java.io.IOException;
import java.net.UnknownHostException;

import resources.Utilities;

public class MTMultiClient {		
	
		public static void bigintTransmit(int port, String ipv4, BigInteger Bmsg, String pathName){
			
			Socket clientSock = null;
			ServerSocket serverSock = null;
			String msg = null;
			OutputStream os = null;
			InputStream is = null;
			OutputStreamWriter osw = null;
			BufferedInputStream bis = null;
			FileInputStream fis = null;
			File myFile=null;
			int num_of_chunks=29;
			int filetype=1;
			
			
			try{
				clientSock = new Socket(ipv4,port);
				System.out.println("Connecting...");
				
				ObjectOutputStream oos = new ObjectOutputStream(clientSock.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(clientSock.getInputStream());
				oos.writeObject(Bmsg);
				System.out.println("Wrote in outputstream:"+Bmsg);
				msg = (String) ois.readObject();
				
				if (msg.equals("Received"))
					System.out.println("OK");
				
			
				
				
				
				
			}catch(UnknownHostException uhe){
				System.out.println(uhe);
			}catch(IOException ioe){
				System.out.println(ioe);
			}catch(ClassNotFoundException cnfe){
				System.out.println(cnfe);
			}finally {
		    	try{
		    		if (clientSock!=null) clientSock.close();
			    	}catch (IOException e){
			    		System.out.println(e);
			    		}
			    }
			
			
				
				
			
			
			
		}
		
		public static void fileTransmit(int port, String ipv4, String pathName){
			
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
		
public static void multipleFileTransmit(int port, String ipv4, String pathName,int num_of_files){
			
		    FileInputStream fis = null;
		    BufferedInputStream bis = null;
		    Socket clientSock = null;
		    OutputStream os = null;
		    File myFile=null;
		    
		    
		    try {
		      clientSock = new Socket(ipv4, port);
		      System.out.println("Connecting...");
		      for(int i=0;i<num_of_files;i++){
		    	  if(i<9)
		    	  myFile = new File (pathName+".00"+(i+1));
		    	  if(i>8)
		    	  myFile = new File (pathName+".0"+(i+1));
	          byte [] mybytearray  = new byte [(int)myFile.length()];
	          fis = new FileInputStream(myFile);
	          bis = new BufferedInputStream(fis);
	          bis.read(mybytearray,0,mybytearray.length);
	          os = clientSock.getOutputStream();
	          System.out.println("Sending " + pathName + "(" + mybytearray.length + " bytes)");
	          os.write(mybytearray,0,mybytearray.length);
		      }
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
		



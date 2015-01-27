package transmission;

import java.io.*;
import java.net.Socket;

public class CLIENTHandler implements Runnable {

	 
	 private final static int FILE_SIZE=6022386;
	 
	
	 private Socket clientSocket;
	 int bytesRead;
	 int current = 0;
	 FileOutputStream fos = null;
	 BufferedOutputStream bos = null;
	 
	 
	 
	public CLIENTHandler(Socket client) {
        this.clientSocket = client;
    }
	
	public void run() {
        try {
        	byte [] mybytearray  = new byte [FILE_SIZE];
            InputStream is = clientSocket.getInputStream();
            String tempfilename ="temp"+System.nanoTime();
            fos = new FileOutputStream(tempfilename);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            do {
               bytesRead =
                  is.read(mybytearray, current, (mybytearray.length-current));
               if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(mybytearray, 0 , current);
            bos.flush();
            System.out.println("File " + tempfilename+ " downloaded (" + current + " bytes read)");
            }
        	catch (IOException e){
        		System.out.println(e);
        	}
        	finally {
        		try{
        			if (fos != null) fos.close();
        			if (bos != null) bos.close();
        			if (clientSocket != null) clientSocket.close();
        		}catch(IOException e){
        		System.out.println(e);
        		}
        	}
          
          
        	
        	
        }
}


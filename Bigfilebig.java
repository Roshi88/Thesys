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
import resources.Structures.*;
import transmission.newClientHandler;
import transmission.ServerHandler;


public class Bigfilebig {
	
	

	public static void main(String[] args) throws IOException, InterruptedException {
		
		int n=5;
		PaillierPrivateKey[] NodePRs = new PaillierPrivateKey[5];
		PPTKverify pv=new PPTKverify();
		SSverify sv=new SSverify();
		PaillierPrivateThresholdKey PPTK;
		BigInteger SS;
		
			
		for (int i=0;i<n;i++){
			NodePRs[i]=Generation.retrieveMyPrivateKey((i+1), "chiavi statiche");
		}
		System.out.println("Loaded passwords");	
		
		String tmp= "123123123";
		BigInteger BI=new BigInteger(tmp);
		System.out.println("BI is: "+BI);
		
		Thread t = new Thread(new ServerHandler(NodePRs[1],pv,sv),"Receiving Server");
		t.start();
		
		PPTK=pv.get();
		System.out.println("My PPTK in main thread is: "+PPTK.getN());
		
		SS=sv.get();
		System.out.println("My Encrypted SS after wait is:"+SS);
		
		
	}

}

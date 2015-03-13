package transmission;

import java.io.*;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.regex.Pattern;
import java.math.BigInteger;

import paillierp.Paillier;
import paillierp.PartialDecryption;
import paillierp.key.PaillierPrivateKey;
import paillierp.key.PaillierPrivateThresholdKey;
import resources.Utilities;
import resources.Structures.*;

public class newClientHandler implements Runnable {

	 
	 
	 
	
	 private Socket clientSocket;
	 private PaillierPrivateKey PrivKey;
	 private PPTKverify pv;
	 private SSverify sv;
	 private PDMcombine pdmc;
	 PaillierPrivateThresholdKey result;
	 int msgtype=-1;
	 int num_of_rx_cnks=-1;
	 
	 
	public newClientHandler(Socket client, PaillierPrivateKey PR, PPTKverify pv, SSverify sv, PDMcombine pdmc) {
        this.clientSocket = client; 
        this.PrivKey = PR;
        this.pv = pv;
        this.sv= sv;
        this.pdmc=pdmc;
        
    }
	
	
	
//I CAN RECEIVE 3 TYPES OF MESSAGES: SHARE, THE ENCRYPTED PASSWORD, THE 4 PDMS
	public void run() {
		try{
			
			 ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
			 ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			 BigInteger preamble = (BigInteger) ois.readObject();
			 
			 
			 System.out.println("Received Preamble is:"+preamble);
			 oos.writeObject("Received preamble");
			 BigInteger[] msg =(BigInteger[]) ois.readObject();
//			 System.out.println("Received Message is:"+msg+"\n"+msg[0]+"\n"+msg[2]);
			 
			 

			 String sPlain = Utilities.bigIntegerToString(preamble);
			 String[] splitArr=Pattern.compile("-").split(sPlain);
			 msgtype=Integer.parseInt(splitArr[0]);
			 num_of_rx_cnks=Integer.parseInt(splitArr[1]);
			 System.out.println("Message type: "+msgtype+"\n"+"Number of received cnks: "+num_of_rx_cnks);
			 

			 //a questo punto ho i miei 29 biginteger. Li devo sistemare uno accanto all'altro e rimettere nel file. 
			 
			 Paillier dsys=new Paillier();
			 dsys.setDecryptEncrypt(PrivKey);
			
			 switch(msgtype){
			 
			 case 1: //Share received
				 
				 System.out.println("Received the share");
				 for(int i=0;i<num_of_rx_cnks;i++){
						String name = new String();
						if(i<9){
							name="Cyph2"+".00"+(i+1);
						}
						if(i>8){
							name="Cyph2"+".0"+(i+1);
						}
						Utilities.newBigIntegerToFile(msg[i], name);
					}
					
					Utilities.retrieveShare(PrivKey, 2,"myShare");
					
////////////////A QUESTO PUNTO HO CREATO IL FILE, E LO VADO AD ELABORARE NEL 3D PRINCIPALE////////////////
					
					int l, w;
					BigInteger v, n, shares, combineSharesConstant;
					BigInteger[]  viarray=new BigInteger[5];
					
					try {  
						FileReader File= new FileReader("myShare");
						BufferedReader buf=new BufferedReader(File);
						String line=buf.readLine();
						l = Integer.parseInt(line.split(":")[1]);
						
						line = buf.readLine();
						w = Integer.parseInt(line.split(":")[1]);
						
						line = buf.readLine();
						v = new BigInteger(line.split(":")[1]);
						
						line = buf.readLine();
						n = new BigInteger(line.split(":")[1]);
						
						line = buf.readLine();
						combineSharesConstant = new BigInteger(line.split(":")[1]);
						
						line = buf.readLine();
						shares = new BigInteger(line.split(":")[1]);
						
						for(int i=0; i<5; i++){
						line = buf.readLine();
						viarray[i] = BigInteger.ZERO;
						}
					buf.close();
					SecureRandom rnd = new SecureRandom();
					
					

					result = new PaillierPrivateThresholdKey(n, l, combineSharesConstant, w, v, 
							viarray, shares, 2, rnd.nextLong());//il 2 qua è il nodeID
					
					pv.put(result);
					
					}catch(IOException e){
						System.out.println(e);
					}
				 break;
				 
			 case 2: // Session Secret received
				 
				 System.out.println("Encrypted Session Secret received");
				 
				 //qua ho un array di BigInteger che devo rimettere uno accanto all'altro
				 
				 //metto ogni BigInteger in un file, quindi mergio i file e lo rimetto in un BigInt unico
				 for(int i=0;i<num_of_rx_cnks;i++){
					 String name=new String();
					 name="eSStoBI.00"+(i+1);
					 Utilities.newBigIntegerToFile(msg[i], name);
				 }
				 Utilities.listAndMergeFiles("eSStoBI", num_of_rx_cnks);
				 
				 // a questo punto ho un file chiamato eSStoBI.rec contenente il mio BigInteger su diverse linee unico
				
				 String tmp = new String();
				 try{
						FileReader File= new FileReader("eSStoBI.rec");
						BufferedReader buf=new BufferedReader(File);
						for(int i=0;i<num_of_rx_cnks;i++){
						String line=buf.readLine();
						tmp=tmp+line;
						}
						buf.close();
					}catch(IOException e){
						System.out.println(e);
					}
				 
				 BigInteger SS=new BigInteger(tmp);
				 sv.put(SS);
				 
				 break;
				 
			 case 3: //PDM received
				 
				 //PROBLEMA, SE MI ARRIVA UN PDM MENTRE STO ESEGUENDO QUESTA ROUTINE
					
				 	
				 	PartialDecryption PDM1=Utilities.retrievePDM(num_of_rx_cnks, msg, 1, PrivKey);
					
					pdmc.putWithPosition(PDM1, 0);				 
				 break;
				 
			 case 4: //PDM received
				 
				 //PROBLEMA, SE MI ARRIVA UN PDM MENTRE STO ESEGUENDO QUESTA ROUTINE
					
				 	
				 	PartialDecryption PDM2=Utilities.retrievePDM(num_of_rx_cnks, msg, 2, PrivKey);
					
					pdmc.putWithPosition(PDM2, 1);				 
				 break;	 
				 
			 case 5: //PDM received
				 
				 //PROBLEMA, SE MI ARRIVA UN PDM MENTRE STO ESEGUENDO QUESTA ROUTINE
					
				 	
				 	PartialDecryption PDM3=Utilities.retrievePDM(num_of_rx_cnks, msg, 3, PrivKey);
					
					pdmc.putWithPosition(PDM3, 2);				 
				 break;	
				 
			 case 6: //PDM received
				 
				 //PROBLEMA, SE MI ARRIVA UN PDM MENTRE STO ESEGUENDO QUESTA ROUTINE
					
				 	
				 	PartialDecryption PDM4=Utilities.retrievePDM(num_of_rx_cnks, msg, 4, PrivKey);
					
					pdmc.putWithPosition(PDM4, 3);				 
				 break;	
				 
			 case 7: //PDM received
				 
				 //PROBLEMA, SE MI ARRIVA UN PDM MENTRE STO ESEGUENDO QUESTA ROUTINE
					
				 	
				 	PartialDecryption PDM5=Utilities.retrievePDM(num_of_rx_cnks, msg, 5, PrivKey);
					
					pdmc.putWithPosition(PDM5, 4);				 
				 break;					 
			 
			 }//end switch
			 



		
		}catch(IOException ioe){
			System.out.println(ioe);
		}catch(ClassNotFoundException cnfe){
			System.out.println(cnfe);
		}finally {
	    	try{
		         
		          if (clientSocket!=null) clientSocket.close();
		    	}catch (IOException e){
		    		System.out.println(e);
		    		}
		    }
          
        	
        	
        }
}


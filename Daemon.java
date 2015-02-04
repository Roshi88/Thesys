import transmission.*;
import resources.*;
import resources.Structures.*;
import paillierp.key.PaillierPrivateKey;
import paillierp.key.PaillierKey;
import paillierp.Paillier;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.math.BigInteger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



public class Daemon {

	
	public static void main(String[] args) throws IOException {
		
//////////////////////////////////    NODE PARAMETERS    //////////////////////////////////
	
	//Node ID of this node
	int nID=1;
	
	//IP's list file position
	String iplist="/home/elia/Desktop/thesys/Thesys/src/files/ip_list";
	
/////////////////////    Generation of Key Couple    /////////////////////////////////////////	
	
	//Generation of my public and private key
	PaillierPrivateKey MyPr = Generation.coupleGen(nID,32);
	BigInteger Pub= MyPr.getN();
	PaillierKey PU=MyPr.getPublicKey();
	
	//Generation of pri,pub key of the other nodes (just for simulation)
	
	PaillierPrivateKey[] NodePris = new PaillierPrivateKey[4];
	
	for (int i=2; i<6;i++){
		NodePris[i-2]=Generation.coupleGen(i,32);
		
		while(NodePris[i-2].getN().toByteArray().length!=9){
			NodePris[i-2]=Generation.coupleGen(i, 32);
		}
		
	}
	
		
	
//////////////////////////   Number of IP Addresses inside of ip_list   /////////////////////
	
	//Counting how many IPs i have in my ips list
	int count=0;
	
	count=Utilities.numOfLines(iplist);
	System.out.println("Number of IPs in ip list: "+count);


/////////////////////    Generation of (n,s) Shares n number of shares, s key length  ////////
	
	Generation.shareGen(5,32);
	
////////////////////    Insertion of template number into share files    /////////////////////
	
	
	//Array of file names containing keys
	String[] chiavi = new String[count];
	for (int i=0; i<count; i++){
	chiavi[i] = "chiave" + (i+1);
		}
	//Array of template file names containing keys
	String[] keys = new String[count];
	for (int i=0; i<count; i++){
	keys[i] = "Tkey" + (i+1);
		}
	
	for (int i=0; i<(count);i++){
		Utilities.insertTemplateNum(chiavi[i],keys[i] , 1);
	}
	
	
////////////////////////// split shares' files in xx byte files    ///////////////////////////////////

	
//count è il numero di nodi
//size è il numero di spezzoni di file
for (int i=0;i<4;i++){
int sizeofn = NodePris[i].getN().toByteArray().length;
System.out.println("Size of n:"+sizeofn);}
	
int sizeofn=8;
File res= new File(keys[0]);
int size = (int)(res.length()/(sizeofn-1))+1;
BigInteger[][] Bi = new BigInteger[count-1][size];	




System.out.println("Size is:"+size);
	
for (int i=1; i<count;i++){
	
	Utilities.splitFile(new File(keys[i]),(sizeofn-1)); //in questo modo divido i file in dimensioni pari ad n
	
	}
	
for (int i=0;i<(count-1);i++){
	
	for (int j=0;j<size;j++){
		
		if(j<9){
		Bi[i][j]=Utilities.fileToBigInteger("Tkey"+(i+2)+".00"+(j+1));
		}
		if(j>8){
			Bi[i][j]=Utilities.fileToBigInteger("Tkey"+(i+2)+".0"+(j+1));
		}
		
	}
	
}

//Ora ho una matrice le cui celle sono spezzoni di file.

//La prima colonna rappresenta i primi spezzoni degli n file
//La prima riga rappresenta gli spezzoni del primo file
//ORA DEVO CRIPTARE OGNI PARTE DI FILE CON UNA CHIAVE PUBBLICA (PU) E CONTROLLARE LA DIMENSIONE DI USCITA DEL FILE

//adesso devo criptare ogni spezzone della stessa riga con la stessa chiave.
//Devo avere le chiavi in versione PaillierKey



//Cripto la prima riga

Paillier esys = new Paillier();
esys.setDecryptEncrypt(MyPr);
BigInteger[] C1 = new BigInteger[size]; //per ogni riga (cioè per ogni file) ho size(29)sottocyphs

System.out.println(Bi[1][1].toByteArray().length);
System.out.println(MyPr.getN().toByteArray().length);

for (int i=0;i<size;i++){
	C1[i]=esys.encrypt(Bi[0][i]);
	System.out.println(C1[i]);
	String name= new String();
	if(i<9){
		name = "Cyph2.00"+(i+1);
	}
	if(i>8){
		name = "Cyph2.0"+(i+1);
	}
	Utilities.bigIntegerToFile(C1[i], name);
}

//devo rimettere ogni BigInteger in un file chiamato Cyphx.00etc





//rimetto in un solo file la prima riga (mi servirà dopo dati tutti i biginteger)


//ArrayList<File> arf = new ArrayList<File>();
//File [] files =new File[size];
//for(int i=0; i<size;++i){
//	if(i<9)
//		files[i]=new File ("Cyph2.00"+(i+1));
//	if(i>8)
//		files[i]=new File ("Cyph2.0"+(i+1));
//	arf.add(files[i]);
//}
//File result = new File ("Cyph2.rec");
//
//if(!result.exists())
//	result.createNewFile();
//Utilities.mergeFiles(arf, result);

//Il file Cyph2.rec è il merge dei singoli file criptati
		
/////////////////////     Opening MultiThreaded Server    //////////////////////////////////
	
//	MTServer.serverStart(8080);
		
	 ServerSocket serverSocket=null; // defining a server socket to listen data
     Socket clientSocket = null; // defining a client socket to send data
	
    final int port=8080; 
 	int i=0;
	try {
        serverSocket = new ServerSocket(port); // Opening a server socket to listen for client calls
        System.out.println("Server started.");
    } catch (Exception e) {
        System.err.println("Port already in use.");
        System.exit(1);
    }
	
	while (true) {
        
		try {
        	
            clientSocket = serverSocket.accept(); //binding server socket to client socket incoming call and accepting call
            System.out.println("Accepted connection : " + clientSocket);
            i=i+1;
            Thread t = new Thread(new CLIENTHandler(clientSocket),"thread"+i); //Create a new thread to handle the single call coming from one client
            System.out.println("Thread "+t.getName()+" is starting");
            t.start(); //Starting the run method contained in CLIENTHandler class

        } catch (Exception e) {
            System.err.println("Error in connection attempt.");
        }
    
       	
	
	}//end while

		
		
		
		
		
		
		
		
	}

}

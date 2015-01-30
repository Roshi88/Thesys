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


public class Daemon {

	
	public static void main(String[] args) throws IOException {
		
//////////////////////////////////    NODE PARAMETERS    //////////////////////////////////
	
	//Node ID of this node
	int nID=1;
	
	//IP's list file position
	String iplist="/home/elia/Desktop/thesys/Thesys/src/files/ip_list";
		
		
		
//////////////////////////    IPV4 address of this node    //////////////////////////////////
	
	//Creating an InetAddress object containing my IP address
		
	InetAddress MyIP=Utilities.retrieveIP("192.168.1.1");
	System.out.println(MyIP);	
	
	InetAddress IP2=Utilities.retrieveIP("192.168.1.2");
	System.out.println(IP2);
	BigInteger Pub2=new BigInteger("14323241558373291833");
	
	InetAddress IP3=Utilities.retrieveIP("192.168.1.3");
	System.out.println(IP3);
	BigInteger Pub3=new BigInteger("9412274574912913409");
	
	InetAddress IP4=Utilities.retrieveIP("192.168.1.4");
	System.out.println(IP4);
	BigInteger Pub4=new BigInteger("7463061501062165399");
	
	InetAddress IP5=Utilities.retrieveIP("192.168.1.5");
	System.out.println(IP5);
	BigInteger Pub5=new BigInteger("12086758193826994631");
	
	
/////////////////////    Generation of Key Couple    /////////////////////////////////////////	
	
	//Generation of my public and private key
	PaillierPrivateKey MyPr = Generation.coupleGen(nID);
	BigInteger Pub= MyPr.getN();
	PaillierKey PU=MyPr.getPublicKey();
		
/////////////////////////    Filling NodeInfo of all nodes    //////////////////////////////
	
	//Creating a node Info object containing my node IP, ID, Public Password
	nodeInfo myNode= new nodeInfo(nID,Pub,MyIP);
	nodeInfo node2= new nodeInfo(2,Pub2,IP2);
	nodeInfo node3= new nodeInfo(3,Pub3,IP3);
	nodeInfo node4= new nodeInfo(4,Pub4,IP4);
	nodeInfo node5= new nodeInfo(5,Pub5,IP5);
	
	
//////////////////////////   Number of IP Addresses inside of ip_list   /////////////////////
	
	//Counting how many IPs i have in my ips list
	int count=0;
	
	count=Utilities.numOfLines(iplist);
	System.out.println("Number of IPs in ip list: "+count);


/////////////////////    Generation of (n,s) Shares n number of shares, s key length  ////////
	
	Generation.shareGen(5,64);
	
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
	
	
////////////////////////// split shares' files in 64 byte files    ///////////////////////////////////

	
//count è il numero di nodi
//size è il numero di spezzoni di file
	
File res= new File(keys[0]);
int size = (int)(res.length()/64)+1;
BigInteger[][] Bi = new BigInteger[count-1][size];	




System.out.println("Size is:"+size);
	
for (int i=1; i<count;i++){
	
	Utilities.splitFile(new File(keys[i]));
	
	}
	
for (int i=0;i<(count-1);i++){
	
	for (int j=0;j<size;j++){
		
		Bi[i][j]=Utilities.fileToBigInteger("Tkey"+(i+2)+".00"+(j+1));
		
	}
	
}

//ORA DEVO CRIPTARE OGNI PARTE DI FILE CON UNA CHIAVE PUBBLICA (PU) E CONTROLLARE LA DIMENSIONE DI USCITA DEL FILE
//QUINDI RICOSTRUIRE IL FILE E TRASMETTERLO
//QUINDI RISPLITTARLO CON BLOCCHI DI DIMENSIONE COME QUELLA USCITA AL PUNTO 1 ^^^
//DECRIPTARE OGNI BLOCCO E RICOSTRUIRE IL FILE ORIGINARIO 

	
///////////////////    Transform files in BigInteger    ///////////////////////////////////////
	
//	BigInteger[] Bi = new BigInteger[count];
//	
//	for (int i=0; i<count;i++){
//		
//		Bi[i]=Utilities.fileToBigInteger(keys[i]);
//	
//		System.out.println(Bi[i]);
//	}
	
////////////    Encrypt the file containing the share with the DST Public Key    /////////////
	
//	Paillier[] esys = new Paillier[count-1];
//	for (int i=0; i<count-1;i++){
//		esys[i]=new Paillier();
//	}
//	
//	esys[0].setEncryption(PU);
//	
//	BigInteger C = esys[0].encrypt(Bi[1]);
	
	
		
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

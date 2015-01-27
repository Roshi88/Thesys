import transmission.*;
import resources.*;
import resources.Structures.*;
import paillierp.key.PaillierPrivateKey;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.math.BigInteger;


public class Daemon {

	
	public static void main(String[] args) {
		
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
	
	count=Utilities.numOfIPs(iplist);
	System.out.println("Number of IPs in ip list: "+count);
		
		
//////////////////////////   Filling Array of MANET IP Addresses    //////////////////////////
		
	//Creating an Array containing all the ips that i have in the ip list
	//InetAddress[] MANIPs=Utilities.arrayOfIPs(iplist, count);


		
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

import transmission.*;
import resources.*;
import resources.Structures.*;
import classes.*;


import java.net.InetAddress;
import java.math.BigInteger;


public class Daemon {

	
	public static void main(String[] args) {
		
//////////////////////////////////    NODE PARAMETERS    //////////////////////////////////
	
	//Node ID of this node
	int nID=1;
	
	//IP's list file position
	String iplist="/home/elia/Desktop/thesys/Thesys/src/files/ip_list";
		
		
		
//////////////////////////    IPV4 address of this node    //////////////////////////////////
		
	InetAddress MyIP=null;
		
	MyIP=Utilities.retrieveIP("192.168.1.1");
	System.out.println(MyIP);
	
	
/////////////////////    Generation of Key Couple    /////////////////////////////////////////	
		
	BigInteger Pub = Generation.coupleGen(nID);
	
		
/////////////////////////    Filling NodeInfo of this node    //////////////////////////////
	
	nodeInfo myNode= new nodeInfo(nID,Pub,MyIP);
		
		
//////////////////////////   Number of IP Addresses inside of ip_list   /////////////////////
		
	int count=0;
	
	count=Utilities.numOfIPs(iplist);
	System.out.println("Number of IPs in ip list: "+count);
		
		
//////////////////////////   Filling Array of MANET IP Addresses    //////////////////////////
		
	InetAddress[] MANIPs=Utilities.arrayOfIPs(iplist, count);


		
/////////////////////     Opening MultiThreaded Server    //////////////////////////////////
		
	MTServer.serverStart(8080);
		
		
		

		
		
		
		
		
		
		
		
	}

}

package resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;

public class Utilities {

	public static InetAddress retrieveIP(String ipv4){
	
		 InetAddress MyIP=null;
		
		try {
			MyIP=InetAddress.getByName(ipv4);
		}
		catch(IOException e){
			System.out.println(e);
		} 
		
		return MyIP;
	}
	
	
	public static int numOfIPs(String iplist){
		int count=0;
		try {			
			FileReader File= new FileReader(iplist);
			BufferedReader buf=new BufferedReader(File);
			while(buf.readLine()!=null)count++;
		}
		catch (IOException e){
			System.out.println(e);
		}	
		
		return count;
	}
	
	public static InetAddress[] arrayOfIPs(String pathname, int count){
		
		InetAddress[] MANIPs=new InetAddress[count];
		
		try {			
			FileReader File= new FileReader(pathname);
			BufferedReader buf=new BufferedReader(File);
			String line=null;
			
			for (int i=0; i<count; i++){
			line = buf.readLine();
			MANIPs[i]=InetAddress.getByName(line);	
			System.out.println((i+1)+"° IP Address: "+MANIPs[i]);
			}		
		}
		catch (IOException e){
			System.out.println(e);
			
		}
		return MANIPs;
		
	}
	
	
	
}

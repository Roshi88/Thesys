package resources;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Structures {
	
	//NODEINFO STRUCTURE, CONTAINING NODE ID, IPv4 ADDRESS OF THE NODE AND PUBLIC PW

	public static class nodeInfo {
		
	public static int NodeID=0;
	public static BigInteger Pub = BigInteger.ZERO;
	public static InetAddress Ipv4 = null;
		
		//empty constructor
		public nodeInfo(){
			
			this.NodeID=-1;
			this.Pub=BigInteger.ZERO;
			try{
				this.Ipv4=InetAddress.getByName("localhost");}
			catch(UnknownHostException uhe){
				System.out.println(uhe);
			}
		}
		
		//constructor
		public nodeInfo(int nid, BigInteger pub, InetAddress ipv4){
			
			this.NodeID=nid;
			this.Pub=pub;
			this.Ipv4=ipv4;
		}
		
		public int getNodeID(){
			
			return this.NodeID;
		}
		
		public BigInteger getPub(){
		
			return this.Pub;
		}
		
		public InetAddress getIPv4(){
			
			return this.Ipv4;
		}
		
		public void setNodeID(int nid){
		
			this.NodeID=nid;
		}
		
		public void setPub(BigInteger n){
			
			this.Pub=n;
			
		}
		
		public void setIPv4(InetAddress ipv4){
			
			this.Ipv4=ipv4;
		}
	}
	
	/////////////////////////////END NODEINFO STRUCTURE
	
}

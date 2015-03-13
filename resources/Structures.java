package resources;

import java.math.BigInteger;

import java.net.InetAddress;
import java.net.UnknownHostException;

import paillierp.key.PaillierPrivateThresholdKey;
import paillierp.PartialDecryption;

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
	
	public static class PPTKverify {
		
		private boolean token=false;
		private PaillierPrivateThresholdKey PPTK;
		
		public synchronized void put(PaillierPrivateThresholdKey PrTK){
		//to put the ready PPTK in the PPTKverify class
			
			PPTK=PrTK;
			token=true; //PPTK is usable
			notify();
		}
		
		public synchronized PaillierPrivateThresholdKey get(){
			
			while(!token)
				try{
					wait();
				}catch(InterruptedException exc){
					exc.printStackTrace();
				}
			
			return PPTK;
		}
	}//end PPTKverify
	
public static class SSverify {
		
		private boolean token=false;
		private BigInteger SS;
		
		public synchronized void put(BigInteger SS){
		//to put the ready PPTK in the PPTKverify class
			
			this.SS=SS;
			token=true; //PPTK is usable
			notify();
		}
		
		public synchronized BigInteger get(){
			
			if(!token)
				try{
					wait();
				}catch(InterruptedException exc){
					exc.printStackTrace();
				}
			
			return SS;
		}
	}//end SSverify
	
public static class PDMcombine{
	
	
	private boolean cToken=false;
	private boolean[] tokenArray=new boolean[5];
	private PartialDecryption[] PDMArray=new PartialDecryption[5];
	private int PDMC=0;
	
	
	public synchronized int getPDMCounter(){
		PDMC++;
		return PDMC-1;
	}
	
//	public synchronized void put(PartialDecryption PDM,int count){
//		if (count==0)
//			this.PDMArray[0]=PDM;
//		else
//			if(count==1)
//				this.PDMArray[1]=PDM;
//			else
//				if(count==2)
//					this.PDMArray[2]=PDM;
//				else
//					if(count==3)
//						this.PDMArray[3]=PDM;
//						cToken=true;
//						notify();
//	}
	
	public synchronized void putWithPosition(PartialDecryption PDM, int positionInPdmArray){
		
		this.PDMArray[positionInPdmArray]=PDM;
		this.tokenArray[positionInPdmArray]=true;
		
		if(tokenArray[0]||tokenArray[1]||tokenArray[2]||tokenArray[3]||tokenArray[4])
			notify();
	}
	
	
	public synchronized PartialDecryption[] get(){
		
		if(!tokenArray[0]||!tokenArray[1]||!tokenArray[2]||!tokenArray[3]||!tokenArray[4])
			try{
				wait();
			}catch(InterruptedException exc){
				exc.printStackTrace();
			}
		
		return PDMArray;
	}
	
	
	
	
}//end PDMcombine




}

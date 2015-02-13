import paillierp.key.*;
import resources.Generation;
import resources.Utilities;

import java.io.IOException;
import java.math.BigInteger;

public class NewDaemon {

	public static void main(String[] args) {
		
		
		int n=5;
		BigInteger[] shares = new BigInteger[n];
		int[] sharesLng = new int[n];
		int[] nLng = new int[n];
		int size_of_cnk=7;
		
/////////////////////////////////LOADING MANET PUBLIC KEYS///////////////////////////////////////
		
		PaillierPrivateKey[] NodePRs = new PaillierPrivateKey[5];
		
		for (int i=0;i<n;i++){
			NodePRs[i]=Generation.retrieveMyPrivateKey((i+1), "chiavi statiche");
			nLng[i] = NodePRs[i].getN().toByteArray().length;
			System.out.println(nLng[i]);
		}

/////////////////////////////////////GENERATING SHARES//////////////////////////////////////////
		
		try{
			Generation.shareGen(5, 32);
		}catch(IOException e){
			System.out.println(e);
		}
		
		for(int i=0;i<n;i++){
			shares[i] = Utilities.fileToBigInteger("chiave"+(i+1));
			
			sharesLng[i]=shares[i].toByteArray().length;
			System.out.println(shares[i]+"\n"+sharesLng[i]);
			
			
		}

		System.out.println("Bitlength: "+shares[0].bitLength()+"\n"+shares[0].toString().length());
		
		BigInteger[] C = Utilities.splitBigInteger(shares[0], 7);
		System.out.println(C[0]+"\n"+C[(C.length)-1]);//si aggira sui 67

		System.out.println("Lunghezza di C: "+C.length); // con C.length ottengo quanti elementi ha C
		
		
		
		
		
		
		
	}//main end

}//newdaemon end

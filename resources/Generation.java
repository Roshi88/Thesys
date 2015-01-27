package resources;
import java.io.FileWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.math.BigInteger;
import paillierp.key.KeyGen;
import paillierp.key.PaillierKey;
import paillierp.key.PaillierPrivateKey;



public class Generation {

// Generazione della coppia di chiavi
	
	public static PaillierPrivateKey coupleGen(int nID){
		
		int nodeID=nID;
		int s=32;
		Random random = new Random();
		long seed = random.nextLong();

		//Creo la chiave privata pr
		
		PaillierPrivateKey pr= KeyGen.PaillierKey(s, seed);
		
		//Trovo la chiave pubblica pu, da pr
		
		PaillierKey pu= pr.getPublicKey();
		
		//System.out.println("PublicKey:("+pr.getN()+","+pr.getNPlusOne()+")");
		
		

		//Arrivato a questo punto Ho la coppia chiave pubblica e chiave privata
		
		//Adesso devo mettere la chiave pubblica in un file, da trasmettere
		
		String pubkey ="src/files/PublicKey"+nodeID;
		
		
		
		
			try {FileWriter File= new FileWriter(pubkey);
			
			PrintWriter out=new PrintWriter(File);
			out.println("n:" + pu.getN());
			out.println("n+1:" + pu.getNPlusOne());
			out.println("n^s:" + pu.getNS());
			out.println("n^s+1:" + pu.getNSPlusOne());
		//	out.println("rnd:" + pu.getRnd());
			out.println("k:" + pu.getK());	
			out.close();}
			catch(IOException e){
				System.out.println(e);
			}
			return pr;
	}
	
}

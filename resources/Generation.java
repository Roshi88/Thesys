package resources;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import paillierp.key.KeyGen;
import paillierp.key.PaillierKey;
import paillierp.key.PaillierPrivateKey;
import paillierp.key.PaillierPrivateThresholdKey;



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
	
	
	public static void shareGen(int n, int s)throws IOException{
		
		int l=n;
		
		int w= (l/2)+1;
		
		System.out.println("w: "+w);
		
		// devo generare l chiavi a 32 bit
		
		long ls = 000001100000;
		
		
		// genero il file con tutte le l chiavi
		
		
		String kiavi ="chiavi";
		
		KeyGen.PaillierThresholdKey(kiavi, s, l, w, ls);
		
		
		
		
		
		String[] chiavi = new String[l];
		for (int i=0; i<l; i++){
		chiavi[i] = "chiave" + (i+1);
			}
		// questo sotto per avere le stesse key di "chiavi" suddivise in 5 file
		
		PaillierPrivateThresholdKey[] keys = KeyGen.PaillierThresholdKeyLoad(kiavi);
		
		
		
		// PaillierPrivateThresholdKey[] keys = KeyGen.PaillierThresholdKey(s, l, w, ls); // questo per creare 5 nuove chiavi diverse dal file chiavi
		for(int i = 0; i < keys.length; i++) {
		FileWriter File= new FileWriter(chiavi[i]);
		PrintWriter out=new PrintWriter(File);
		out.println("l:" + l);
		out.println("w:" + w);
		out.println("v:" + keys[0].getV());
		out.println("n:" + keys[0].getN());
		out.println("combineSharesConstant:" + keys[0].getCombineSharesConstant());
		out.println("s"  + ":" + keys[i].getSi().toString());
		out.println("v"  + ":" + keys[i].getVi()[i].toString()); // out.println("v"+ i + ":" + keys[i].getVi()[i].toString()) +i se si vuole si e vi
		
		out.close();
		}
		
	}
	
}

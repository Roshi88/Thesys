package classes;

import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;

import paillierp.key.KeyGen;
import paillierp.key.PaillierPrivateThresholdKey;
import paillierp.key.PaillierThresholdKey;
import paillierp.PaillierThreshold;
import paillierp.AbstractPaillier;
import paillierp.PartialDecryption;


public class GroupAuthority {

	public static void main(String[] args) throws IOException {
		

		// dopo aver fatto una qualche autenticazione, ottengo un l.
		
		// decido quanto deve valere w
		
		int l=5;
		
		int w= (l/2)+1;
		
		System.out.println("w: "+w);
		
		// devo generare l chiavi a 32 bit
		
		long ls = 000001100000;
		int s = 32;
		
		// genero il file con tutte le l chiavi
		
		
		KeyGen K = new KeyGen();
		
		
		String kiavi ="chiavi";
		
		K.PaillierThresholdKey(kiavi, s, l, w, ls);
		
		
		
		
		
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
		
		// a questo punto ho un file contenente tutte le chiavi, e l file ognuno con una chiave
		
		// cerco la chiave pubblica corrispondente alle chiavi appena create e la uso per criptare un messaggio;
		
		//ottengo la chiave pubblica (non so come farlo al momento)
		
		// creo la session key
		
		BigInteger Plain = BigInteger.valueOf(123123123);
		BigInteger r = BigInteger.valueOf(1283712638); //numero random che serve per criptare con paillier
		
		System.out.println("La Session Key è: "+Plain);
		
		// cripto la session key
		
		
		
		BigInteger Cyph = AbstractPaillier.encrypt(Plain,r, keys[0].getThresholdKey()); // cifro la session key con r e la chiave pubblica ottenuta dagli share della privata
		BigInteger Cyph2 = AbstractPaillier.encrypt(Plain,r, keys[1].getThresholdKey());
		BigInteger Cyph3 = AbstractPaillier.encrypt(Plain,r, keys[2].getThresholdKey());
		BigInteger Cyph4 = AbstractPaillier.encrypt(Plain,r, keys[3].getThresholdKey());
		BigInteger Cyph5 = AbstractPaillier.encrypt(Plain,r, keys[4].getThresholdKey());
		
		System.out.println(Cyph); // Session key cifrata con la chiave pubblica
		
		System.out.println(Cyph2);
		System.out.println(Cyph3);
		System.out.println(Cyph4);
		System.out.println(Cyph5);
		
		
		
		// ricavo i pdm tutti in questo file, in realtà li dvorò rimettere insieme in qualche modo
		
		PartialDecryption [] PDMN = new PartialDecryption [l];
		
		for(int i=0; i<l; i++){	
			
		PDMN [i] = new PartialDecryption(keys[i], Cyph); // decripto la session key con ogni share
		
		
		System.out.println("PDM"+PDMN[i].getID());
		
		
		System.out.println(PDMN[i].getDecryptedValue());
		
		
		
		} // fine for
		
		// porto la PDM su file
		
		BigInteger Dv;
		int Id;
		String pdms = new String ("pdm"+PDMN[0].getID());
		
		FileWriter File = new FileWriter(pdms);
		PrintWriter out = new PrintWriter(File);
		
		out.println("DV:" + PDMN[0].getDecryptedValue());
		out.println("Id:" + PDMN[0].getID());
		out.close();
		
		// fine di porto la PDM su file
		
		// dal file porto la PDM dentro un oggetto di tipo PartialDecryption
		
		  
			FileReader File2= new FileReader(pdms);
			BufferedReader buf=new BufferedReader(File2);
			String line=buf.readLine();
			Dv = new BigInteger(line.split(":")[1]);
			line = buf.readLine();
			Id = Integer.parseInt(line.split(":")[1]);
			buf.close();
			//creo l'oggetto PartialDecrypt con i parametri appena ricavati
			
			PartialDecryption Abba = new PartialDecryption(Dv,Id);
			
		 	
		
		
		// fine upload da file
		
		
		
		PaillierThreshold Message = new PaillierThreshold(keys[0].getThresholdKey());
		Message.setDecryptEncrypt(keys[0]);
		
		System.out.println(Message.combineShares(Abba,PDMN[1],PDMN[2],PDMN[3],PDMN[4]));
		
		
	} // fine main

} // fine class

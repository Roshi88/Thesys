import paillierp.key.PaillierPrivateKey;
import paillierp.key.PaillierKey;
import paillierp.Paillier;
import resources.Generation;
import resources.Utilities;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;


public class testino {

	public static void main(String[] args) throws IOException {
		
		int n=5; // numero di nodi della MANET
		
		PaillierPrivateKey[] MyNodesPrs = new PaillierPrivateKey[5];
		for (int i=0;i<n;i++){
			MyNodesPrs[i]=Generation.coupleGen((i+1), 32);
			while(MyNodesPrs[i].getN().toByteArray().length!=8)
				MyNodesPrs[i]=Generation.coupleGen((i+1), 32);
		}
				
		Generation.shareGen(n, 32); //genero i 5 file contenti gli shares 
		
		String[] chiavi = new String[n];
		String[] keys = new String[n];
		
		for (int i=0; i<n; i++){
			chiavi[i] = "chiave" + (i+1);
			keys[i] = "Tkey" + (i+1);
			Utilities.insertTemplateNum(chiavi[i],keys[i] , 1);
		}
		
		//Misuro lunghezza file chiavi
		
		long[] dims = new long[5];
		

		for (int i=0;i<n;i++){
			File tmp= new File(keys[i]);
			dims[i]=tmp.length();
			System.out.println(dims[i]);
		}
		
		final double MAX=202;
		
		double chunk_size=7;
		double a=MAX/chunk_size;
		System.out.println("Number of chunks: "+a);
		int num_of_chks=(int)Math.ceil(a);
		System.out.println("Rounded Number of chunks: "+num_of_chks);
		
		Utilities.splitFile(new File(keys[0]),(int)chunk_size);//divido il file in "num_of_chks" files di dimensione "chunk_size"
		
		BigInteger[] B= new BigInteger[num_of_chks];
		ArrayList<File> arf = new ArrayList<File>();
		File [] files =new File[num_of_chks];
		
		for (int i=0;i<num_of_chks;i++){
			
			if(i<9){
				B[i]=Utilities.fileToBigInteger("Tkey1.00"+(i+1));
				}
			if(i>8){
				B[i]=Utilities.fileToBigInteger("Tkey1.0"+(i+1));
				}
			
		}
		
		Paillier esys = new Paillier();
		Paillier dsys = new Paillier();
		esys.setEncryption(MyNodesPrs[0].getPublicKey());
		dsys.setDecryptEncrypt(MyNodesPrs[0]);
		BigInteger[] C = new BigInteger[num_of_chks];
		BigInteger[] T = new BigInteger[num_of_chks];
		BigInteger[] M = new BigInteger[num_of_chks];
		BigInteger[] R = new BigInteger[num_of_chks];
		
		for (int i=0;i<num_of_chks;i++){
			System.out.println("B is: "+B[i]);
			C[i]=esys.encrypt(B[i]);
			System.out.println("C is: "+C[i]);
			T[i]=dsys.decrypt(C[i]);
			System.out.println("T is: "+T[i]);
			
			String name= new String();
			if(i<9){
				name = "Cyph1.00"+(i+1);
			}
			if(i>8){
				name = "Cyph1.0"+(i+1);
			}
			Utilities.newBigIntegerToFile(C[i], name); //ho il BigInt in un file
			M[i]=Utilities.newFileToBigInteger(name); //riprendo il BigInt dal file
			System.out.println("M is: "+M[i]);
			
			R[i]=dsys.decrypt(M[i]);
			System.out.println(R[i]);
			
			String outfname= new String();
			if(i<9){
				outfname = "rxPlain1.00"+(i+1);
			}
			if(i>8){
				outfname = "rxPlain1.0"+(i+1);
			}
			
			
			Utilities.newBigIntegerToFile(R[i], outfname);
			//adesso devo ricostruire
			
			
			
			if(i<9)
				files[i]=new File ("rxPlain1.00"+(i+1));
			if(i>8)
				files[i]=new File ("rxPlain1.0"+(i+1));
			arf.add(files[i]);
			
			File res = new File ("rxPlain1.rec");
	
			
			
			
			if(!res.exists())
				res.createNewFile();
			Utilities.mergeFiles(arf, res);
			
			
		}
		
		
		
		
		
		
		
		
		
		
	}//fine main

}//fine class testino

package classes;

import resources.*;
import resources.Generation;

import java.io.File;

import paillierp.Paillier;
import paillierp.key.PaillierKey;
import paillierp.key.PaillierPrivateKey;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import resources.Utilities;


public class prova {

	public static void main(String[] args) throws IOException {

		
		PaillierPrivateKey MyPr = Generation.coupleGen(1,32);
		BigInteger Pub= MyPr.getN();
		PaillierKey PU=MyPr.getPublicKey();
		
		
		Random random = new Random();
		long seed = random.nextLong();
		PaillierKey pro1 = new PaillierKey(Pub.toByteArray(),seed);
		System.out.println(pro1.getN());
		
		BigInteger m =new BigInteger("12312312312");
		
		
		Paillier esys1=new Paillier();
		esys1.setDecryptEncrypt(MyPr);
		
		Paillier esysp=new Paillier();
		esysp.setEncryption(pro1);
		esysp.setDecryption(MyPr);
		
		BigInteger c1 = esys1.encrypt(m);
		BigInteger cp = esysp.encrypt(m);
		
		BigInteger m1 = esys1.decrypt(c1);
		
		BigInteger mp = esysp.decrypt(cp);
		System.out.println(m1);
		System.out.println(mp);
		
		
		
//		Utilities.splitFile(new File("ip_list"));
		
		
//		ArrayList<File> arf = new ArrayList<File>();
//		File [] files =new File[3];
//		for(int i=0; i<3;++i){
//			files[i]=new File ("ip_list.00"+(i+1));
//			arf.add(files[i]);
//		}
//		File res = new File ("ip_list.rec");
//
//		
//		
//		
//		if(!res.exists())
//			res.createNewFile();
//		Utilities.mergeFiles(arf, res);
	}

}

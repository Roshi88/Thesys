package resources;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import paillierp.key.PaillierPrivateKey;

public class keys {

	public static void main(String[] args)throws IOException {
		
		PaillierPrivateKey[] NodePRKeys = new PaillierPrivateKey[5];
		
		for(int i=0;i<5;i++){
			NodePRKeys[i]=Generation.coupleGen(i, 32);
			while(NodePRKeys[i].getN().toByteArray().length!=8)
				NodePRKeys[i]=Generation.coupleGen((i+1), 32);
		}
		
		FileWriter File= new FileWriter("chiavi statiche");
		PrintWriter out=new PrintWriter(File);
		for (int i=0;i<5;i++){
		out.println("d"+(i+1)+":" + NodePRKeys[i].getD());
		out.println("dinv"+(i+1)+":" + NodePRKeys[i].getDInvs());
		out.println("n"+(i+1)+":" + NodePRKeys[i].getN());
		}
		out.close();
	}

}

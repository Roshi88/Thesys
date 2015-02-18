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
		
		BigInteger uno = BigInteger.valueOf(123);
		BigInteger due = BigInteger.valueOf(456);
		
		BigInteger res= new BigInteger(uno.toString()+due.toString());
		System.out.println(res);
		
		
		
		
		
		
	}//fine main

}//fine class testino

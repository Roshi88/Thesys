import java.math.BigInteger;
import java.util.regex.Pattern;

import transmission.*;
import resources.Utilities;

public class test {

	public static void main(String[] args) {

	BigInteger[] Bmsg= new BigInteger[5];
	String sPreamble="1-29";
	BigInteger preamble=Utilities.stringToBigInteger(sPreamble);
	
	for (int i=0;i<5;i++){
		Bmsg[i]=BigInteger.valueOf(1234567);
	}
		MTMultiClient.bigintTransmit(8080, "localhost", Bmsg,preamble);
 //     MTClient.fileTransmit(8080, "localhost", "ip_list");


	}

}

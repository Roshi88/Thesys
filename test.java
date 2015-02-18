import java.math.BigInteger;
import java.util.regex.Pattern;

import transmission.*;
import resources.Utilities;

public class test {

	public static void main(String[] args) {

		BigInteger Preamble = Utilities.stringToBigInteger("1-3");
		BigInteger[] Msg = new BigInteger[3];
	
		for (int i=0;i<3;i++){
			Msg[i]=BigInteger.valueOf((i+1));
		}
	
		MTMultiClient.bigintTransmit(8080, "localhost", Msg,Preamble);
 //     MTClient.fileTransmit(8080, "localhost", "ip_list");


	}

}

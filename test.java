import java.math.BigInteger;
import java.util.regex.Pattern;

import transmission.*;
import resources.Utilities;

public class test {

	public static void main(String[] args) {

		BigInteger B = BigInteger.valueOf(12345678);
		BigInteger[] C = Utilities.splitBigInteger(B, 3);
		System.out.println(C[0]+"\n"+C[1]+"\n"+C[2]);
		
//		MTMultiClient.bigintTransmit(8080, "localhost", Bmsg,"Cyph4");
 //     MTClient.fileTransmit(8080, "localhost", "ip_list");


	}

}

import java.math.BigInteger;
import java.util.regex.Pattern;

import transmission.*;
import resources.Utilities;

public class test {

	public static void main(String[] args) {

		
		
//		BigInteger Bmsg=BigInteger.valueOf(123123123);
//		MTMultiClient.bigintTransmit(8080, "localhost", Bmsg,"Cyph4");
 //     MTClient.fileTransmit(8080, "localhost", "ip_list");
		
		 String sMsg="1-29";
		 String[] splitArr=Pattern.compile("-").split(sMsg);
		 System.out.println(splitArr[0]);
		 System.out.println(splitArr[1]);

	}

}

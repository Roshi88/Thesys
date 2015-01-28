import java.math.BigInteger;

import resources.Utilities;

public class test {

	public static void main(String[] args) {

		
//      MTClient.fileTransmit(8080, "localhost", "ip_list");
//      MTClient.fileTransmit(8080, "localhost", "ip_list");
		
		Utilities.insertTemplateNum("ip_list","ip_list_templated", 1);
		
		BigInteger msg = Utilities.fileToBigInteger("ip_list");
		
		String res = Utilities.bigIntegerToString(msg);//per riportare da BigInteger a Stringa
		
		System.out.println(msg);
		System.out.println(res);
		
		//BigInteger to file
		Utilities.bigIntegerToFile(msg, "BItoFile");
		
		Utilities.removeTemplateNum("ip_list_templated","ip_list_fixed");
	}

}

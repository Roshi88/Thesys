import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.io.IOException;


public class Bigfilebig {

	public static void main(String[] args) throws IOException {
		//scrivo su file il mio biginteger
		
		BigInteger C=BigInteger.valueOf(123123123);
		String fname = "puppa";
		FileWriter File= new FileWriter(fname);
		PrintWriter out=new PrintWriter(File);
		
		out.println(C);
		out.close();
		
		//fin qua ho messo il BigInteger sul file
		
		//Adesso devo tirar giu il BigInteger DAL file
		
		FileReader File1= new FileReader(fname);
		BufferedReader buf=new BufferedReader(File1);
		String line=buf.readLine();
		BigInteger M = new BigInteger(line);
		
		System.out.println("C is: "+C);
		System.out.println("M is: "+M);
			
	}

}

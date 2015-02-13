package resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.math.BigInteger;

import paillierp.key.PaillierPrivateKey;
import paillierp.key.PaillierKey;
import paillierp.Paillier;

public class Utilities {
	
	//To port a ipv4 address in String format to InetAddress

	public static InetAddress retrieveIP(String ipv4){
	
		 InetAddress MyIP=null;
		
		try {
			MyIP=InetAddress.getByName(ipv4);
		}
		catch(IOException e){
			System.out.println(e);
		} 
		
		return MyIP;
	}
	
	//To get number of lines contained in a file
	
	public static int numOfLines(String iplist){
		int count=0;
		BufferedReader buf=null;
		try {			
			FileReader File= new FileReader(iplist);
			buf=new BufferedReader(File);
			while(buf.readLine()!=null)count++;
		}catch (IOException e){
			System.out.println(e);
		}finally{
			try{
				if(buf!=null)
					buf.close();
			}catch(IOException e){
				System.out.println(e);
			}
		}
		
		return count;
	}
	
	//To obtain an array of InetAddresses given a file and relative number of IPs (non serve)
	
	public static InetAddress[] arrayOfIPs(String pathname, int count){
		
		InetAddress[] MANIPs=new InetAddress[count];
		BufferedReader buf=null;
		try {			
			FileReader File= new FileReader(pathname);
			buf=new BufferedReader(File);
			String line=null;
			
			for (int i=0; i<count; i++){
			line = buf.readLine();
			MANIPs[i]=InetAddress.getByName(line);	
			System.out.println((i+1)+"° IP Address: "+MANIPs[i]);
			}		
		}catch (IOException e){
			System.out.println(e);			
		}finally{
			try{
				if(buf!=null)
					buf.close();
			}catch(IOException e){
				System.out.println(e);
			}
		}
		return MANIPs;
		
	}
	
	//to split a file into chunks of given size
	
	
	
	    public static void splitFile(File f,int sizeofchunk) throws IOException {
	        int partCounter = 1;//I like to name parts from 001, 002, 003, ...
	                            //you can change it to 0 if you want 000, 001, ...

	        int sizeOfFiles = sizeofchunk;//split file size
	        byte[] buffer = new byte[sizeOfFiles];

	        try (BufferedInputStream bis = new BufferedInputStream(
	                new FileInputStream(f))) {//try-with-resources to ensure closing stream
	            String name = f.getName();

	            int tmp = 0;
	            while ((tmp = bis.read(buffer)) > 0) {
	                //write each chunk of data into separate file with different number in name
	                File newFile = new File(f.getParent(), name + "."
	                        + String.format("%03d", partCounter++));
	                try (FileOutputStream out = new FileOutputStream(newFile)) {
	                    out.write(buffer, 0, tmp);//tmp is chunk size
	                }
	            }
	        }
	    }
	
	//to merge x chunks of file in one file
	    
	    public static void mergeFiles(List<File> files, File into)
	            throws IOException {
	        try (BufferedOutputStream mergingStream = new BufferedOutputStream(
	                new FileOutputStream(into))) {
	            for (File f : files) {
	                Files.copy(f.toPath(), mergingStream);
	            }
	        }
	    }
	
	
	
	
	
	
	
	
	
	
	//to convert a file into a string
	
	
	public static String fileToString(String pathname){
		byte[] encoded=null;
		try{
			encoded = Files.readAllBytes(Paths.get(pathname));
			
		}catch(IOException e){
			System.out.println(e);
		}
		return new String(encoded);
		 
		
		
	}
	
	//to convert a string into a BigInteger
	
	public static BigInteger stringToBigInteger(String pathname){
		byte[] bytemsg=pathname.getBytes();
		BigInteger m=new BigInteger(bytemsg); 

		return m;
		
		
	}
	
	//To convert a BigInteger into a String
	
	public static String bigIntegerToString(BigInteger m){
		
		String out=new String(m.toByteArray());
		
		return out;
	}
	
	
	//To convert a file into a BigInteger
	
	public static BigInteger fileToBigInteger(String pathname){
		
		String prova=Utilities.fileToString(pathname);		
		BigInteger msg =Utilities.stringToBigInteger(prova);		
		
		return msg;
	}
	
	//To convert a BigInteger into a file
	
	public static void bigIntegerToFile(BigInteger m, String outFileName){
		
		String res = Utilities.bigIntegerToString(m);
		
		try {
			Files.write(Paths.get(outFileName),res.getBytes());
		}catch(IOException e){
			System.out.println(e);
		}
		
		
		
	}
	
	//BigInteger to file with Paillier Library method
	
	public static void newBigIntegerToFile(BigInteger b,String outFileName){
		
		try{
		FileWriter File= new FileWriter(outFileName);
		PrintWriter out=new PrintWriter(File);
		
		out.println(b);
		out.close();
		}catch(IOException e){
			System.out.println(e);
		}
		
	}
	
	//File to BigInteger with Paillier Library method
	
	public static BigInteger newFileToBigInteger(String inFileName){
		BigInteger M=null;
		try{
		FileReader File= new FileReader(inFileName);
		BufferedReader buf=new BufferedReader(File);
		String line=buf.readLine();
		M = new BigInteger(line);
		buf.close();
		}catch(IOException e){
			System.out.println(e);
		}
		return M;
		
	}
	
	
	
	

	public static void insertTemplateNum(String pathnamein,String pathnameout, int tmpNo){
		
		String oldFileName=pathnamein;
		String tmpFileName=pathnameout;
				
		BufferedReader br=null;
		BufferedWriter bw=null;
		
		try{
			br=new BufferedReader(new FileReader(oldFileName));
			bw=new BufferedWriter(new FileWriter(tmpFileName));
			String line;
			bw.write("t="+tmpNo+"\n");
			while((line=br.readLine())!=null){
				bw.write(line+"\n");
			}
			
		}catch(IOException e){
			System.out.println(e);
			return;
		}finally{
			try{
				if(br!=null)
					br.close();
			}catch(IOException e){
				System.out.println(e);
			}
			try{
				if(bw!=null)
					bw.close();
			}catch(IOException e){
				System.out.println(e);
			}
		
		}
		
		
		
		
	}
	
	public static void removeTemplateNum(String pathnamein, String pathnameout){
		
		String oldFileName=pathnamein;
		String tmpFileName=pathnameout;
				
		BufferedReader br=null;
		BufferedWriter bw=null;
		
		try{
			br=new BufferedReader(new FileReader(oldFileName));
			bw=new BufferedWriter(new FileWriter(tmpFileName));
			String line;
			br.readLine();
			while((line=br.readLine())!=null){
				bw.write(line+"\n");
			}
			
		}catch(IOException e){
			System.out.println(e);
			return;
		}finally{
			try{
				if(br!=null)
					br.close();
			}catch(IOException e){
				System.out.println(e);
			}
			try{
				if(bw!=null)
					bw.close();
			}catch(IOException e){
				System.out.println(e);
			}
		
		}
		
		
		
	}
	
	public static void retrieveShare(PaillierPrivateKey Pr,int nID){
		
		String name= new String();
		String outfname= new String();
		BigInteger[] C = new BigInteger[29];
		BigInteger[] M = new BigInteger[29];
		ArrayList<File> arf = new ArrayList<File>();
		File [] files =new File[29];
		Paillier dsys = new Paillier();
		dsys.setDecryptEncrypt(Pr);
		
		for(int i=0;i<29;i++){
			
			if(i<9){
				name = "Cyph"+nID+".00"+(i+1);
				outfname = "rxPlain"+nID+".00"+(i+1);
				files[i]=new File ("rxPlain"+nID+".00"+(i+1));
			}
			if(i>8){
				name = "Cyph"+nID+".0"+(i+1);
				outfname = "rxPlain"+nID+".0"+(i+1);
				files[i]=new File ("rxPlain"+nID+".0"+(i+1));
			}
		
			C[i]=Utilities.newFileToBigInteger(name);
			M[i]=dsys.decrypt(C[i]);
			Utilities.bigIntegerToFile(M[i], outfname);
				
			arf.add(files[i]);
			
			File res = new File ("rxPlain"+nID+".rec");
	
			
			
			try{
			if(!res.exists())
				res.createNewFile();
			Utilities.mergeFiles(arf, res);
			}catch(IOException e){
				System.out.println(e);
			}
		
		}
		
	}//end of retrieveShare
	
	public static void listAndMergeFiles(String inFileName,int num_of_chks){
		
		ArrayList<File> arf = new ArrayList<File>();
		File [] files =new File[num_of_chks];
		for(int i=0; i<num_of_chks;++i){
			
			if(i<9)
				files[i]=new File (inFileName+".00"+(i+1));
			if(i>8)
				files[i]=new File (inFileName+".0"+(i+1));
			arf.add(files[i]);
		}
		File res = new File (inFileName+".rec");

		
		
		try{
		if(!res.exists())
			res.createNewFile();
		Utilities.mergeFiles(arf, res);
		}catch(IOException e){
			System.out.println(e);
		}
		
	}
	
	
	public static BigInteger encryptTipeAndChunks(int type, int num_of_chunks, PaillierKey Pub){
	
		String msg=new String(type+"-"+num_of_chunks);
		Paillier esys = new Paillier();
		esys.setEncryption(Pub);
		BigInteger B = Utilities.stringToBigInteger(msg);
		BigInteger C = null;
		C=esys.encrypt(B);
		
		return C;
		
	}
	
	public static BigInteger[] splitBigInteger(BigInteger m, double size_of_cnk){
		
		
		String str = m.toString();
		double lng = str.length();
		double a=(lng)/size_of_cnk;
		int num_of_cnk=(int)Math.ceil(a);
		BigInteger[] B = new BigInteger[num_of_cnk];
		
		
		//c'ho l'ultimo da sistemare, perchè il secondo indice quando arrivo all'ultimo chunk non è multiplo di 7
		
		
		for (int i=0;i<num_of_cnk;i++){
			if(i<(num_of_cnk-1)){
				String tmp = str.substring((i*(int)size_of_cnk), ((i+1)*(int)size_of_cnk));
				B[i]=new BigInteger(tmp);
			}
			if(i==(num_of_cnk-1)){
				String tmp = str.substring((i*(int)size_of_cnk), str.length());
				B[i]=new BigInteger(tmp);
			}
			
		}
		
		return B;
	
	
	
	
	
	
	
	
	
	}// end splitBigInteger
}

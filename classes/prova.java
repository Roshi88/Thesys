package classes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import resources.Utilities;


public class prova {

	public static void main(String[] args) throws IOException {

		Utilities.splitFile(new File("ip_list"));
		
		
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

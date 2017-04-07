package H3;

import java.io.File;

public class PrelezAdresare {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(prelez("C:\\Users\\holas3"));
		
	}
	
	public static int prelez(String odkial){
		System.out.println(odkial);
		int pocet = 0;
		File f = new File(odkial);
		File[] pole = f.listFiles();
		if (pole != null){
			for (int i = 0; i < pole.length; i++){
				if (pole[i] != null){
					if (pole[i].isDirectory()){
						pocet += prelez(pole[i].getAbsolutePath());
					}
					else{
						if (pole[i].getName().endsWith(".java")){
							pocet += pole[i].length();
						}
					}
					
				}
			}
		}
		return pocet;
		
	}

}

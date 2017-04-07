package H3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class matica {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Scanner sc = new Scanner(new File("matica.txt"));
			PrintStream vystup = new PrintStream("vystup.txt");
			int n = sc.nextInt();
			double[][] matica = new double[n][n];
			for (int i = 0; i < n; i++){
				for (int j = 0; j < n; j++){
					matica[i][j] = sc.nextDouble();
				}
			}
			
			for (int i = 0; i < n; i++){
				for (int j = 0; j < n; j++){
					vystup.printf("%7.2f", matica[j][i]);
					
				}
				vystup.println();
			}
			sc.close();
			vystup.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

}

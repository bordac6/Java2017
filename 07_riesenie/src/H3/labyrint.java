package H3;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class labyrint {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File f = new File("labyrint.txt");
		FileInputStream fi = new FileInputStream(f);
		Scanner sc = new Scanner(fi);
		Vector<String> v = new Vector<String>();
		while(sc.hasNextLine()){
			v.add(sc.nextLine());
		}
		char[][] pole = new char[v.size()][];
		for(int i=0; i< v.size(); i++){
			pole[i] = v.get(i).toCharArray();
		}
		System.out.println(v);
	}

}

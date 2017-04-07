import java.io.*;

public class Pismena {
	/** 
	 * citame riadky vstupneho suboru a vypisujeme do vystupneho
	 *  okrem toho prebehneme riadok cyklom a pomocou funkcie isLowerCase zratame pocet malych pisemok
	 *  asi viac v tomto kode nenajdete...
     * @param args program nevyuziva argumenty prikazoveho riadku
	 */
	public static void main(String[] args) {
	  try {	
		BufferedReader in = new BufferedReader(new FileReader("PISMENA.TXT"));
		//BufferedWriter out = new BufferedWriter(new FileWriter("KOLKO.TXT"));
		PrintStream out = new PrintStream("KOLKO.TXT");
		for(;;) {
			String line = in.readLine();
			if (line == null) break;
			out.println(line);
			//out.write(line);
			//out.newLine();
			int count = 0;
			for(int i=0; i<line.length(); i++)
			  if (Character.isLowerCase(line.charAt(i)))
				  count++;
			//out.write(""+count);
			//out.newLine();
			out.println(""+count);
		}
		in.close();
		out.close();
	  } catch (Exception E) {
		  System.err.println("Error:" + E.getMessage());
	  }		
	}
}

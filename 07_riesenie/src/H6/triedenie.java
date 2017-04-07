package H6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class triedenie {
	public static void triedenie() throws IOException {
		long cs = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new FileReader("vstup.txt"));
		String line;
		ArrayList<Integer> a = new ArrayList<Integer>();
		while ((line = br.readLine()) != null) {
			a.add(Integer.parseInt(line));
		}
		long ck = System.currentTimeMillis();
		System.out.println(ck - cs);
		a.clear();
		cs = System.currentTimeMillis();
		Scanner s = new Scanner(new File("vstup.txt"));
		while (s.hasNextInt())
			a.add(s.nextInt());
		s.close();
		ck = System.currentTimeMillis();
		System.out.println(ck - cs);
		// System.out.println(a);
		Collections.sort(a);
		// System.out.println(a);
		br.close();
		PrintStream bs = new PrintStream(new File("vystup.txt"));
		for (Integer i : a) {
			bs.println(i);
		}
		bs.close();

	}

	public static void pismena() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("PISMENA.TXT"));
			String line;
			PrintStream ps = new PrintStream("KOLKO.TXT");
			while ((line = br.readLine()) != null) {
				// a.add(Integer.parseInt(line));
				int count = 0;
				for (int i = 0; i < line.length(); i++) {
					if (line.charAt(i) >= 'a' && line.charAt(i) <= 'z') {
						count++;
					}
				}
				ps.println(line);
				ps.println(count);
			}
			ps.close();
			br.close();
		} catch (IOException e) {
			System.out.println("chyba");
		}

	}

	public static void matica() {
		try {
			int[][] matica = new int[4][4];
			Scanner sc = new Scanner(new File("15.in"));
			int r = 0;
			int s = 0;
			while (sc.hasNextInt()) {
				matica[r][s] = sc.nextInt();
				s++;
				if (s == 4) {
					r++;
					s = 0;
				}
			}
			while (sc.hasNext()) {
				System.out.println(sc.next());
			}
			sc.close();
		} catch (IOException e) {
			System.out.println("chyba");
		}
	}

	public static void tajnaSprava() throws FileNotFoundException {
		LinkedList<File> q = new LinkedList<File>();
		q.add(new File("."));
		//System.out.println(q);
		while (!q.isEmpty()) {
			File f = q.remove();
			if (f.isDirectory()) {
				for (File file : f.listFiles()) {
					q.add(file);
				}
			}
			else {
				Scanner sc = new Scanner(f);
				while (sc.hasNextLine()) {
					String s = sc.nextLine();
					if (s.contains("TAJNA SPRAVA")) {
						System.out.println(f);
					}
				}
				sc.close();
			}
			//System.out.println(f);
		}
	}

	public static void sam_seba() throws FileNotFoundException{
		Scanner s = new Scanner(new File("src/triedenie.java"));
		while (s.hasNextLine())
			System.out.println(s.nextLine());
		s.close();


	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// pismena();
		// matica(); //Uloha 4
		/*
		 * BNode<Integer> s = new BNode<Integer>(new BNode<Integer>(null, 1,
		 * null), 2, new BNode<Integer>(null,3,null)); s.write(); BNode<Integer>
		 * s2 = s.load(); System.out.println(s2);
		 */
		//tajnaSprava();
		sam_seba();
		BNode<Integer> s = new BNode<Integer>(new BNode<Integer>(null, 1,
				  null), 2, new BNode<Integer>(null,3,null));
		BNode<Integer> s2 = s.load(); System.out.println(s2);
	}
}

class BNode<E extends Comparable<E>> implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 4000917828458408609L;
	BNode<E> left; // ¾avý podstrom
	E key; // hodnota vrchola
	BNode<E> right; // pravý podstrom

	Integer aa;

	public BNode(BNode<E> left, E key, BNode<E> right) { // konštruktor
		this.left = left;
		this.key = key;
		this.right = right;
	}

	public void write() throws FileNotFoundException, IOException {
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File("novy.dat")));
		os.writeObject(this);
		os.close();
	}

	public String toString() {
		String lavy = "";
		if (left != null) {
			lavy = left.toString();
		}
		String pravy = "";
		if (right != null) {
			pravy = right.toString();
		}
		return lavy + " " + key + " " + pravy + " " + aa;

	}

	public BNode<E> load() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream os = new ObjectInputStream(new FileInputStream(new File("novy.dat")));
		BNode<E> b = (BNode<E>) os.readObject();
		os.close();
		return b;
	}

}

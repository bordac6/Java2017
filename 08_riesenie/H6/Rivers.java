package H6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Rivers {

	final static int n = 10;
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{

		ArrayList<Rieka> list = new ArrayList<>();

		for (int i = 0; i < n; i++){
			Rieka rieka = new Rieka(i);
			rieka.start();
			list.add(rieka);
		}

		while(true){
			String riadok = printuj(list);
			System.out.println(riadok);
			Thread.sleep(250);

		}


	}

	public static String printuj(ArrayList<Rieka> l){

		String ret = "                                                                                ";

		StringBuilder sb = new StringBuilder();
		sb.append(ret);
		for (Rieka r : l){
			if (sb.charAt(r.col) == 'o'){
				r.alive = false;
			}
			if (r.alive) sb.setCharAt(r.col, 'o');

		}

		return sb.toString();
	}

}
class Rieka extends Thread{

	int index;
	int col;
	boolean alive;
	public Rieka(int i){
		this.index = i;
		this.col = new Random().nextInt(80);
		alive = true;
	}

	public void run(){
		while(alive){
			int cas = new Random().nextInt(500)+300;
			try {
				Thread.sleep(cas);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			int pos = new Random().nextInt(3)-1;
			this.col += pos;
			if (this.col > 79) this.col = 79;
			if (this.col < 0) this.col = 0;

		}

	}



}
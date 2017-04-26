import java.util.Random;

public class Rieky {
	Rieka[] stav;
	final int POCET = 10;
	
	public Rieky() {
		stav = new Rieka[POCET];
		PrintThread pt = new PrintThread(this);
		for(int i=0; i<POCET; i++)
			stav[i] = new Rieka(this, i);
		for(int i=0; i<POCET; i++) {
			if (stav[i] != null)
				stav[i].start();
		}
		pt.start();
	}
	synchronized public boolean isPosition(int position) {
		for(Rieka r:stav)
			if (r != null && r.position == position)
				return true;
		return false;
	}
	synchronized public boolean collistion(int id, int position) {
		for(Rieka r:stav)
			if (r != null && r.id != id && r.position == position)
				return true;
		return false;
	}
	synchronized public void remove(int id) {
		for(int i=0; i<POCET; i++) {
			if (stav[i] != null &&  stav[i].id == id)
				stav[i] = null;
		}
	}
	class PrintThread extends Thread {
		public Rieky rieky;
		public PrintThread(Rieky rieky) {
			this.rieky = rieky;
		}
		public void run() {
			while (true) {
				for(int i=0; i<80; i++)
					System.out.print((rieky.isPosition(i))?"o":'.');
				System.out.println();
				try {
					sleep(250);
				} catch(Exception E) {
				}
			}
		}
	}
	class Rieka extends Thread {
		public int position;
		public int delay;
		public int id;
		public Rieky rieky;
		Random rnd = new Random();
		public Rieka(Rieky rieky, int id) {
			this.id = id;
			this.rieky = rieky;
			position = rnd.nextInt(80);
			delay = 350+rnd.nextInt(500);
		}
		
		public void run() {
			while (true) {
				int delta = rnd.nextInt(3)-1;
				position += delta;
				if (position < 0)
					position = 0;
				if (position > 80)
					position = 80;
				if (rieky.collistion(id, position)) {
					rieky.remove(id);
					break;
				}
				try {
					sleep(delay);
				} catch(Exception E) {
				}
			}
		}
	}
	public static void main(String[] args) {
		new Rieky();	
	}
}

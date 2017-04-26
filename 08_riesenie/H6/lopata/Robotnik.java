package H6.lopata;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Robotnik extends Thread {
	Lopata l;

	public Robotnik(int r, Lopata l) {
		super("Robo " + r);
		this.l = l;
	}

	public void run() {
		Random r = new Random();

		while(true) {
			try {
				System.out.println(this.getName()+ " idem brat lopatu..");
				synchronized(l){
					while(l.pocitadlo == 0){
						l.wait();
					}
					System.out.println(this.getName()+ " beriem lopatu a pracujem..");
					l.pocitadlo--;
				}
				sleep(r.nextInt(1000));
				System.out.println(this.getName()+ " vratim lopatu a spim..");
				synchronized(l){
					l.pocitadlo++;
					l.notify();
				}
				sleep(r.nextInt(1000));
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}

	}

}

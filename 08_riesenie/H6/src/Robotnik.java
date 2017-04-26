package H6.src;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Robotnik extends Thread {
	Semaphore s;

	public Robotnik(int r, Semaphore s) {
		super("Robo " + r);
		this.s = s;
	}

	public void run() {
		Random r = new Random();

		while(true) {
			try {
				System.out.println(getName() + " ide skusi vziat lopatu");
				s.acquire();
				System.out.println(getName() + " mam lpatu, idem pracovat");
				sleep(r.nextInt(1000)); //work()
				System.out.println(getName() + " pusta lopatu");
				s.release();
				System.out.println(getName() + " ide spat");
				sleep(r.nextInt(1000));//sleep
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}

package H6.src;

import java.util.concurrent.Semaphore;

public class Lopata {
	public static final int R = 4;
	public static final int N = 2;

	public static Semaphore s = new Semaphore(N);

	public static void main(String[] args) {
		for (int r = 0; r < R; r++) {
			new Robotnik(r, s).start();
		}
	}

}

package H6;

import java.util.Random;

public class Person implements Runnable{

	private Account acc;

	public Person(Account acc) {
		this.acc = acc;
		new Thread(this).start();
	}

	@Override
	public void run() {
		Random rnd = new Random();
		while(true) {
			int amount = rnd.nextInt(1000);
			synchronized(acc) {
				if(acc.getBalance() >= amount) {
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
					acc.withdraw(amount);
				}
			}
		}
	}

	public static void main(String[] args) {
		Account a = new Account(10000);
		int n = 10;
		Person[] ludia = new Person[n];
		for(int i = 0; i < n; i++) {
			ludia[i] = new Person(a);
		}
		while(true) {
			System.out.println(a.getBalance());
		}
	}

}

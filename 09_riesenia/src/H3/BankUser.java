package H3;

import java.util.Random;

public class BankUser extends Thread {
	private String name;
	private BankAccount account;
	Random rng;

	public BankUser (String name, BankAccount acc) {
		this.name = name;
		account = acc;
		rng = new Random();
	}

	@Override
	public void run() {
		while (true) {
			double wAmount = rng.nextDouble()*300;

			synchronized (account) {
				if (account.getBalance() >= wAmount) {
					account.withdraw(wAmount);
					System.out.println(name +  ": vyberam " + wAmount + ".");
				} else {
					System.out.println(name +  ": na ucte nie je dost penazi...dalej nevyberam.");
					break;
				}
			}

			try {
				sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		BankAccount acc = new BankAccount(2000.0);

		BankUser A = new BankUser("A", acc);
		BankUser B = new BankUser("B", acc);

		A.start();
		B.start();
	}

}
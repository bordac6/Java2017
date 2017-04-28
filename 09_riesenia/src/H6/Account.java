package H6;

public class Account {
	private int amount;

	public Account(int amount) {
		this.amount = amount;
	}

	public int getBalance() { return amount;}

	public void withdraw(int amount) {
		this.amount -= amount;
	}
}

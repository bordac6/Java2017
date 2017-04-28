package H3;

public class BankAccount {
	private Double balance;

	public BankAccount(Double balance) {
		this.balance = balance;
	}

	public BankAccount() {
		this(1000d);
	}

	public Double getBalance() {
		return balance;
	}

	public void withdraw(Double amount) {
		balance -= amount;
	}
}

package bank;

public class MyAcct implements BankAccount {
	private boolean open; // Contains yellow underlines
	private int balance;
	private int available_balance;
	private int transaction_limit; // Contains yellow underlines
	private int session_limit; // Contains yellow underlines
	private int total_this_session; // Contains yellow underlines

	public MyAcct(BankDB db, int actnum) {
		int[] data = db.getData(actnum);
		assert(data != null);
		open = (data[0] == 0);
		balance = data[1];
		available_balance = data[2];
		transaction_limit = data[3];
		session_limit = data[4];
		total_this_session = 0;
	}

	@Override
	public int getBalance() {
		return balance;
	}

	@Override
	public int getAvailBalance() {
		if (!open && available_balance != 0)
			throw new IllegalArgumentException("Closed account with non-zero available balance");
		return available_balance;
	}

	@Override
	public boolean deposit(int amt) throws IllegalArgumentException {
		if (!open)
			throw new IllegalArgumentException("Closed account during deposit");
		if (amt < 0)
			return false;
		balance = balance + amt;
		return true;
	}

	@Override
	public boolean withdraw(int amt) throws IllegalArgumentException {
		if (!open)
			throw new IllegalArgumentException("Closed account during withdraw");
		if (amt < 0)
			return false;
		if (amt > balance || amt > available_balance)
			throw new IllegalArgumentException("Balance exceeded during withdraw!");
		if (amt > transaction_limit)
			return false;
		if (amt + total_this_session > session_limit)
			return false;

		balance -= amt;
		available_balance -= amt;
		total_this_session += amt;

//		balance = balance - amt;
//		available_balance = available_balance - amt;
//		total_this_session = total_this_session + amt;
		return true;
	}

}

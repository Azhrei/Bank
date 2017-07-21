package bank;

import exception.AccountDataException;

public class MyAcct implements BankAccount {
	private boolean open; // Contains yellow underlines
	private Currency balance;
	private Currency available_balance;
	private Currency transaction_limit; // Contains yellow underlines
	private Currency session_limit; // Contains yellow underlines
	private Currency total_this_session; // Contains yellow underlines

	public MyAcct(BankDB db, int actnum) {
		int[] data = db.getData(actnum);
		assert (data != null);
		assert (data[0] == 0 || data[0] == 1);
		open = (data[0] == 0);
		balance = new Currency(data[1]);
		available_balance = new Currency(data[2]);
		transaction_limit = new Currency(data[3]);
		session_limit = new Currency(data[4]);
		total_this_session = new Currency(0);
		
		if (balance.getAmount() < 0)
			throw new AccountDataException("Invalid balance");
		if (!(available_balance.getAmount() >= 0 && available_balance.getAmount() <= balance.getAmount()))
			throw new AccountDataException("Invalid available balance");
		if (!(transaction_limit.getAmount() <= session_limit.getAmount()))
			throw new AccountDataException("Invalid transaction limit");
		if (!(total_this_session.getAmount() <= session_limit.getAmount()))
			throw new AccountDataException("Invalid balance");
	}

	@Override
	public Currency getBalance() {
		return balance;
	}

	@Override
	public Currency getAvailBalance() {
		if (!open && available_balance.getAmount() != 0)
			throw new IllegalArgumentException("Closed account with non-zero available balance");
		return available_balance;
	}

	@Override
	public Receipt deposit(Currency amt) throws IllegalArgumentException {
		if (!open)
			throw new IllegalArgumentException("Closed account during deposit");
		if (amt.getAmount() < 0)
			return null;
		int newBal = balance.getAmount() + amt.getAmount();
		balance = new Currency(newBal);
		return new Receipt(true, TransType.Credit, newBal, available_balance.getAmount(), amt.getAmount());
	}

	@Override
	public Receipt withdraw(Currency amt) throws IllegalArgumentException {
		if (!open)
			throw new IllegalArgumentException("Closed account during withdraw");
		if (amt.getAmount() < 0)
			return null;
		if (amt.getAmount() > balance.getAmount() || amt.getAmount() > available_balance.getAmount())
			throw new IllegalArgumentException("Balance exceeded during withdraw!");
		if (amt.getAmount() > transaction_limit.getAmount())
			return null;
		if (amt.getAmount() + total_this_session.getAmount() > session_limit.getAmount())
			return null;

		int newBal = balance.getAmount() - amt.getAmount();
		int newAvailBal = available_balance.getAmount() - amt.getAmount();
		int newTotal = total_this_session.getAmount() + amt.getAmount();
		
		balance = new Currency(newBal);
		available_balance = new Currency(newAvailBal);
		total_this_session = new Currency(newTotal);

		return new Receipt(true, TransType.Debit, newBal, newAvailBal, amt.getAmount());
	}

}

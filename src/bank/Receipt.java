package bank;

enum TransType {
	Query, Credit, Debit, Other
}

public class Receipt {
	private boolean success;
	private TransType t;				// Need to create this class/enum
	private int balance;				// Shouldn't these be 'Currency' objects?
	private int availBalance;
	private int transAmt;
	
	public Receipt(boolean s, TransType trans, int bal, int abal, int amt) {
		success = s;
		t = trans;
		balance = bal;
		availBalance = abal;
		transAmt = amt;
	}

	public boolean isSuccess() {
		return success;
	}
	
	public TransType getTransactionType() {
		return t;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public int getAvailBalance() {
		return availBalance;
	}
	
	public int getTransAmt() {
		return transAmt;
	}
}

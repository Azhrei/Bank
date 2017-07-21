package bank;

public interface BankAccount {
	public Currency getBalance();
	public Currency getAvailBalance();
	
	/**
	 * Adds the given amount to the bank account object's balance.  Does
	 * not affect the available balance or other fields.  Returns true on success,
	 * and false if the amount is less than zero.
	 * <p>
	 * If account is not open, throws <b>IllegalArgumentException</b>.
	 * 
	 * @param amt - amount to deposit (must be non-negative)
	 * @return true if deposit amount successfully applied
	 */
	public Receipt deposit(Currency amt) throws IllegalArgumentException;
	
	/**
	 * Reduces the balance in this bank account object, and returns a true/false status.
	 * If <code>false</code> is returned, no changes to the account are made.
	 * Exceptional conditions are checked in this order:
	 * <ol>
	 * <li>If account is not open, throws <b>IllegalArgumentException</b>.
	 * <li>Negative amounts will return false.
	 * <li>Insufficient funds to perform withdrawal will return false.
	 * <li>Amount to withdraw must be less than or equal to the transaction limit or returns false.
	 * <li>Amount to withdraw must be less than or equal to the session limit (including previous withdrawals) or returns false. 
	 * </ol>
	 * @param amt - amount to withdraw (must be non-negative)
	 * @return true if withdraw amount successfully applied
	 */
	public Receipt withdraw(Currency amt) throws IllegalArgumentException;
}

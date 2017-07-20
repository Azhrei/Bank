package bank;

public interface BankDB {
	/**
	 * Retrieves data from the database.  If a record cannot be found,
	 * it throws an exception.
	 * @param actnum integer account number
	 * @return array of integers representing account data
	 * @throws IllegalArgumentException
	 */
	public int[] getData(int actnum) throws IllegalArgumentException;
	public void putData(int actnum, int[] data);
}

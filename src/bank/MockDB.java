package bank;

public class MockDB implements BankDB {

	@Override
	public int[] getData(int actnum) throws IllegalArgumentException {
		int[][] data = {
				{ 0, 1000, 1000,  100,   500, 1111 },
				{ 1,  587,  346,  100,   800, 2222 },
				{ 0,  897,  239, 1000, 10000, 3333 },
				{ 0,  397,    0,  300,  1000, 4444 },
				{ 0,    0,    0,  100,   500, 5555 },
		};
		for (int[] row : data) {
			if (row[5] == actnum)
				return row;
		}
		throw new IllegalArgumentException("Account data not found");
	}

	@Override
	public void putData(int actnum, int[] data) {
		return;
	}

}

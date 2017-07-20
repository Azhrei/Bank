package bank;

import exception.NoSuchAccountException;

public class MockDB implements BankDB {

	@Override
	public int[] getData(int actnum) throws NoSuchAccountException {
		int[][] data = {
				{ 0, 1000, 1000,  100,    500, 1111 },
				{ 1,   587,   346,  100,     800, 2222 },
				{ 0,   897,  239, 1000, 10000, 3333 },
				{ 0,   397,       0,   300,   1000, 4444 },
				{ 0,        0,       0,   100,     500, 5555 },
				{ 0,  -100,       0,   100,     500, 6666 },	// balance < 0
				{ 0,        0,      -1,   100,     500, 7777 },	// avail bal < 0
				{ 0,        0,       1,   100,     500, 8888 },	// avail bal > bal
				{ 0,        0,       0, 1000,     500, 9999 }, // trans > sess
		};
		for (int[] row : data) {
			if (row[5] == actnum)
				return row;
		}
		throw new NoSuchAccountException("Account data not found");
	}

	@Override
	public void putData(int actnum, int[] data) {
		return;
	}

}

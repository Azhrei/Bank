package bank;

/*
 * Rod Davison has made the two ZIP files in the support/ directory
 * available on GitHub.com at https://github.com/exgnosis/TDD-Files-for-Frank
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.assertj.core.api.Assertions.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exception.AccountDataException;

public class TestBankAccount {
	BankAccount ba;
	static BankDB db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		db = new MockDB();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = exception.NoSuchAccountException.class)
	public void testAccountExists() {
		ba = new MyAcct(db, 1234);
	}

	@Test
	public void testBalance01() {
		ba = new MyAcct(db, 5555);
		assertThat(ba.getBalance()).as("Incorrect available balance").isEqualTo(0);
//		int retVal = ba.getBalance();
//		assertEquals("Incorrect available balance", 0, retVal);
	}

	@Test
	public void testBalance02() {
		ba = new MyAcct(db, 2222);
		assertThat(ba.getBalance()).as("Incorrect available balance").isEqualTo(587);
//		int retVal = ba.getBalance();
//		assertEquals("Incorrect available balance", 587, retVal);
	}

	@Test
	public void testAvailBalance01() {
		ba = new MyAcct(db, 5555);
		assertThat(ba.getBalance()).as("Incorrect available balance").isEqualTo(0);
//		int retVal = ba.getAvailBalance();
//		assertEquals("Incorrect available balance", 0, retVal);
	}

	@Test
	public void testAvailBalance02() {
		ba = new MyAcct(db, 4444);
		assertThat(ba.getBalance()).as("Incorrect available balance").isEqualTo(397);
//		int retVal = ba.getAvailBalance();
//		assertEquals("Incorrect available balance", 0, retVal);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testAvailBalance03() {
		ba = new MyAcct(db, 2222);
		ba.getAvailBalance();
	}

	@Test
	public void testDeposit01() {
		ba = new MyAcct(db, 4444);
		int current = ba.getBalance();
		boolean retVal = ba.deposit(100);
		int difference = ba.getBalance() - current;
//		assertTrue("Deposit failed", retVal);
//		assertEquals("Deposit didn't adjust balance correctly", 100, difference);
		assertThat(retVal).as("Deposit failed").isTrue();
		assertThat(difference).as("Deposit didn't adjust balance correctly").isEqualTo(100);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testDeposit02() {
		ba = new MyAcct(db, 2222);
		ba.deposit(100);
	}

	@Test
	public void testDeposit03() {
		ba = new MyAcct(db, 4444);
		boolean retVal = ba.deposit(-100);
		assertFalse("Deposit of negative amount succeeded!?!?", retVal);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testWithdraw01() {
		ba = new MyAcct(db, 2222);
		ba.withdraw(100);
	}

	@Test
	public void testWithdraw02() {
		ba = new MyAcct(db, 4444);
		boolean retVal = ba.withdraw(-100);
		assertFalse("Withdraw of negative amount succeeded!?!?", retVal);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testWithdraw03() {
		ba = new MyAcct(db, 4444);
		ba.withdraw(497);
	}

	@Test
	public void testWithdraw04() {
		ba = new MyAcct(db, 1111);
		boolean retVal = ba.withdraw(200);
		assertFalse("Withdraw allowed transaction limit to be exceeded", retVal);
	}

	@Test
	public void testWithdraw05() {
		ba = new MyAcct(db, 1111);
		boolean retVal;
		for (int i = 0; i < 5; i++) {
			retVal = ba.withdraw(100);
			assertTrue("Withdraw failed", retVal);
		}
		retVal = ba.withdraw(100);
		assertFalse("Withdraw succeeded but should have failed due to session limit", retVal);
	}

	@Test(expected = AccountDataException.class)
	public void testAccountData01() {
		new MyAcct(db, 6666);
	}

	@Test(expected = AccountDataException.class)
	public void testAccountData02() {
		new MyAcct(db, 7777);
	}

	@Test(expected = AccountDataException.class)
	public void testAccountData03() {
		new MyAcct(db, 8888);
	}

	@Test(expected = AccountDataException.class)
	public void testAccountData04() {
		new MyAcct(db, 9999);
	}
}

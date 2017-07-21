package bank;

// No need for these imports after converting all to AssertJ...
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertThat;
//import static org.junit.Assert.assertTrue;

import static org.assertj.core.api.Assertions.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import exception.AccountDataException;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

/**
 * Rod Davison has made the two ZIP files in the support/ directory
 * available on GitHub.com; see
 * <a href="https://github.com/exgnosis/TDD-Files-for-Frank">https://github.com/exgnosis/TDD-Files-for-Frank</a>
 * <p>
 * The following class uses the Jmockit annotation <code>@RunWith</code>.
 * This annotation causes JUnit to use Jmockit to process the class.  This is
 * necessary to allow Jmockit to review any annotations that it uses, such as
 * <code>@Tested</code> and <code>@Mocked</code>.
 * We use the latter in {@link #testBalance01(MockDB)}.
 * <p>
 * Test methods may contain commented out assertions for JUnit.  If so,
 * they represent code that is no longer necessary after implementing
 * Lab 6-1 using AssertJ.
 * 
 * For Lab 6-3, to run JUnit from the command line on macOS Sierra
 * I had to use a command line that included all necessary JAR files, then
 * specify the name of the JUnit class that contained <code>main()</code>
 * and add the test class name at the end (this is one long line):
 * <br/>
 * <code>java
 * -cp
 * /Users/frank/.m2/repository/junit/junit/4.12/junit-4.12.jar:/Users/frank/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:../support/assertj-core-3.8.0.jar:../support/jmockit-1.8.jar:.
 * org.junit.runner.JUnitCore
 * bank.TestBankAccount</code>
 */
@RunWith(JMockit.class)
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

	/**
	 * This method uses Jmockit to create a mocked BankDB object.
	 * Note the use of the @Mocked annotation on the parameter.
	 * Normally, test methods are called without parameters, but Jmockit
	 * arranges for mock objects to be created on the fly if they appear
	 * as parameters.  This is ideal because the mocked object is scoped
	 * to just this method.  If a mocked object should last longer, such as
	 * for the entire execution of the class, then declare the mocked object
	 * as an instance member with @Mocked and use Expectations in the
	 * instance initializer for the class.  (Read about static and instance
	 * initializers <a href="http://www.javaworld.com/article/3040564/learn-java/java-101-class-and-object-initialization-in-java.html">
	 * in this article at JavaWorld.com</a>.)
	 * @param mockeddb - mocked object injected by Jmockit
	 */
	@Test
	public void testBalance01(@Mocked BankDB mockeddb) {
		new Expectations() {
			{
				mockeddb.getData(5555); result = new int[] { 0, 999, 999, 999, 9999 };
			}
		};
		BankAccount myba = new MyAcct(mockeddb, 5555);
		assertThat(myba.getBalance()).as("Incorrect available balance").isEqualTo(999);
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
		assertThat(ba.deposit(-100)).as("Deposit of negative amount succeeded").isFalse();
//		boolean retVal = ba.deposit(-100);
//		assertFalse("Deposit of negative amount succeeded!?!?", retVal);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testWithdraw01() {
		ba = new MyAcct(db, 2222);
		ba.withdraw(100);
	}

	@Test
	public void testWithdraw02() {
		ba = new MyAcct(db, 4444);
//		boolean retVal = ba.withdraw(-100);
//		assertFalse("Withdraw of negative amount succeeded!?!?", retVal);
		assertThat(ba.withdraw(-100)).as("Withdraw of negative amount succeeded").isFalse();
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testWithdraw03() {
		ba = new MyAcct(db, 4444);
		ba.withdraw(497);
	}

	@Test
	public void testWithdraw04() {
		ba = new MyAcct(db, 1111);
//		boolean retVal = ba.withdraw(200);
//		assertFalse("Withdraw allowed transaction limit to be exceeded", retVal);
		assertThat(ba.withdraw(200)).as("Withdraw allowed transaction limit to be exceeded").isFalse();
	}

	@Test
	public void testWithdraw05() {
		ba = new MyAcct(db, 1111);
//		boolean retVal;
		for (int i = 0; i < 5; i++) {
//			retVal = ba.withdraw(100);
//			assertTrue("Withdraw failed", retVal);
			assertThat(ba.withdraw(100)).as("Withdraw failed").isTrue();
		}
//		retVal = ba.withdraw(100);
//		assertFalse("Withdraw succeeded but should have failed due to session limit", retVal);
		assertThat(ba.withdraw(100)).as("Withdraw succeeded but should have failed due to session limit").isFalse();
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

package bank;

// No need for these imports after converting all to AssertJ...
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertThat;
//import static org.junit.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

/**
 * Rod Davison has made the two ZIP files in the support/ directory
 * available on GitHub.com at {@link https://github.com/exgnosis/TDD-Files-for-Frank}
 */
@RunWith(JMockit.class)
public class TestBankAccount {
	@Tested
	BankAccount ba;
	@Mocked
	MockDB db;

	@Test
	public void testBalance01(@Mocked MockDB db) {
		new Expectations() {
			{
					db.getData(5555); int[] result = { 999, 999, 999, 999, 9999 };
			}
		};
		assertThat(ba.getBalance()).as("Incorrect available balance").isEqualTo(999);
	}
}

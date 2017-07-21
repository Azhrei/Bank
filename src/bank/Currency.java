package bank;

public class Currency {
	private int amount;
	private String cur;		// Defaults to US dollars

	public Currency(int a, String c) {
		amount = a;
		cur = c;
	}

	public Currency(int a) {
		amount = a;
		cur = "USD";
	}

	public int getAmount() {
		return amount;
	}
	
	public String getCur() {
		return cur;
	}
}

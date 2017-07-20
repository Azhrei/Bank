package exception;

public class NoSuchAccountException extends RuntimeException {
	public NoSuchAccountException(String s) {
		super(s);
	}
}

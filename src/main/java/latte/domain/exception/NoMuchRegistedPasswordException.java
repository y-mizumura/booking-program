package latte.domain.exception;

@SuppressWarnings("serial")
public class NoMuchRegistedPasswordException extends BusinessLogicException {
	public NoMuchRegistedPasswordException(String message) {
		super(message);
	}
}

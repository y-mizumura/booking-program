package latte.domain.exception;

@SuppressWarnings("serial")
public class InvalidEntryTimeException extends BusinessLogicException {
	public InvalidEntryTimeException(String message) {
		super(message);
	}
}

package latte.domain.exception;

@SuppressWarnings("serial")
public class UseOtherTableException extends BusinessLogicException {
	public UseOtherTableException(String message) {
		super(message);
	}
}

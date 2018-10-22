package latte.domain.exception;

@SuppressWarnings("serial")
public class NoExistDeleteTargetException extends BusinessLogicException {
	public NoExistDeleteTargetException(String message){
		super(message);
	}
}

package latte.domain.exception;

@SuppressWarnings("serial")
public class AlreadyRegisteredException extends BusinessLogicException{
	public AlreadyRegisteredException(String message){
		super(message);
	}
}

package latte.domain.exception;

@SuppressWarnings("serial")
public class BusinessLogicException extends RuntimeException{
	public BusinessLogicException(String message){
		super(message);
	}
}

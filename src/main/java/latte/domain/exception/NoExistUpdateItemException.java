package latte.domain.exception;

@SuppressWarnings("serial")
public class NoExistUpdateItemException extends BusinessLogicException{
	public NoExistUpdateItemException(String message){
		super(message);
	}
}

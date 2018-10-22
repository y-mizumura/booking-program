package latte.domain.exception;

@SuppressWarnings("serial")
public class ChangeSelfAccountException extends BusinessLogicException{
	public ChangeSelfAccountException(String message){
		super(message);
	}
}

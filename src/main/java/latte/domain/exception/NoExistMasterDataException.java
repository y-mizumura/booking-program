package latte.domain.exception;

@SuppressWarnings("serial")
public class NoExistMasterDataException extends BusinessLogicException {
	public NoExistMasterDataException(String message) {
		super(message);
	}
}

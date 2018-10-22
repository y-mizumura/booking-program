package latte.app.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class NewPasswordMustBeDifferentValidator implements ConstraintValidator<NewPasswordMustBeDifferent, Object>{

	private String message;
	private String currentPW;
	private String newPW;
	
	@Override
	public void initialize(NewPasswordMustBeDifferent annotation) {
		message   = annotation.message();
		currentPW = annotation.currentPW();
		newPW     = annotation.newPW();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		// Formの変数名で取得できるように引数を加工
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		
		String currentPW = (String)beanWrapper.getPropertyValue(this.currentPW);
		String newPW     = (String)beanWrapper.getPropertyValue(this.newPW);
		
		if (currentPW == null || newPW == null) {
			return true;
		}
		
		// 現在のパスワードと新しいパスワードを比較
		// 同じ場合、入力エラー
		boolean isNewPasswordMustBeDifferent = !currentPW.equals(newPW);
		if (!isNewPasswordMustBeDifferent) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addPropertyNode("newPW1").addConstraintViolation();
		}
		
		return isNewPasswordMustBeDifferent;
	}

}

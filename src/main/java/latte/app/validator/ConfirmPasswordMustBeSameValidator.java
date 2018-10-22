package latte.app.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class ConfirmPasswordMustBeSameValidator implements ConstraintValidator<ConfirmPasswordMustBeSame, Object>{

	private String message;
	private String newPW1;
	private String newPW2;
	
	@Override
	public void initialize(ConfirmPasswordMustBeSame annotation) {
		message = annotation.message();
		newPW1  = annotation.newPW1();
		newPW2  = annotation.newPW2();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		// Formの変数名で取得できるように引数を加工
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		
		String pw1 = (String)beanWrapper.getPropertyValue(this.newPW1);
		String pw2 = (String)beanWrapper.getPropertyValue(this.newPW2);
		
		if (pw1 == null || pw2 == null) {
			return true;
		}
		
		// 新しいパスワードと比較
		// 異なる場合、入力エラー
		boolean isConfirmPasswordMustBeSame = pw1.equals(pw2);
		if (!isConfirmPasswordMustBeSame) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addPropertyNode("newPW2").addConstraintViolation();
		}
		
		return isConfirmPasswordMustBeSame;
	}

}

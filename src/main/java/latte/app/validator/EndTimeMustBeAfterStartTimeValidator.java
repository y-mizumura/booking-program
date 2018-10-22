package latte.app.validator;

import java.time.LocalTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class EndTimeMustBeAfterStartTimeValidator
		implements ConstraintValidator<EndTimeMustBeAfterStartTime, Object> {

	private String message;
	private String startTime;
	private String endTime;

	@Override
	public void initialize(EndTimeMustBeAfterStartTime annotation) {
		message   = annotation.message();
		startTime = annotation.startTime();
		endTime   = annotation.endTime();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		// Formの変数名で取得できるように引数を加工
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		
		// Formの変数名（startTime／endTime）の値を取得
		LocalTime startTime = (LocalTime)beanWrapper.getPropertyValue(this.startTime);
		LocalTime endTime   = (LocalTime)beanWrapper.getPropertyValue(this.endTime);;
		
		if (startTime == null || endTime == null) {
			return true;
		}
		
		// endTimeがstartTimeより後であることをチェック
		boolean isEndTimeMustBeAfterStartTime = endTime.isAfter(startTime);
		if (!isEndTimeMustBeAfterStartTime) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addPropertyNode("endTime").addConstraintViolation();
		}

		return isEndTimeMustBeAfterStartTime;
	}

}

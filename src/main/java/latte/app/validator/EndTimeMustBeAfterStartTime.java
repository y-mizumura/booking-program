package latte.app.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = { EndTimeMustBeAfterStartTimeValidator.class })
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface EndTimeMustBeAfterStartTime {
	String message() default "{latte.app.validator.C0102.EndTimeMustBeAfterStartTime.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
	// チェック対象 default値にFormの変数名を指定
	String startTime() default "startTime";
	String endTime() default "endTime";

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		EndTimeMustBeAfterStartTime[] value();
	}
}

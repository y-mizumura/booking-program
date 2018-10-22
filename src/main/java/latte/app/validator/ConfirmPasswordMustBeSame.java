package latte.app.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = { ConfirmPasswordMustBeSameValidator.class })
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ConfirmPasswordMustBeSame {
	String message() default "{latte.app.validator.ConfirmPasswordMustBeSame.message}";
	
	Class<?>[]groups() default {};
	
	Class<? extends Payload>[]payload() default {};
	
	// チェック対象
	String newPW1() default "newPW1";
	String newPW2() default "newPW2";
	
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		ConfirmPasswordMustBeSame[]value();
	}
}

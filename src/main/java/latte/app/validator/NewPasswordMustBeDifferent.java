package latte.app.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;
import javax.validation.*;

@Documented
@Constraint(validatedBy = { NewPasswordMustBeDifferentValidator.class })
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface NewPasswordMustBeDifferent {
	String message() default "{latte.app.validator.NewPasswordMustBeDifferent.message}";
	
	Class<?>[]groups() default {};
	
	Class<? extends Payload>[]payload() default {};

	String currentPW() default "currentPW";
	String newPW() default "newPW1";
	
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		NewPasswordMustBeDifferent[]value();
	}
}

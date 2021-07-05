package pl.sda.matchbetapp.api.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserNameLengthValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserNameLength {
    String message() default "First and last name total length doesn't meet required length";

    int minLength() default 0;
    int maxLength() default 50;

    Class<?>[] groups() default {};
    Class<?extends Payload>[] payload() default {};
}

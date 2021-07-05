package pl.sda.matchbetapp.api.validator;

import pl.sda.matchbetapp.api.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameLengthValidator implements ConstraintValidator <UserNameLength, User> {

    private int minLength;
    private int maxLength;

    @Override
    public void initialize(UserNameLength constraintAnnotation) {
        minLength = constraintAnnotation.minLength();
        maxLength = constraintAnnotation.maxLength();
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
       String name = user.getFirstName() + " " + user.getLastName();
        return name.length() >= minLength && name.length() <= maxLength;
    }

}

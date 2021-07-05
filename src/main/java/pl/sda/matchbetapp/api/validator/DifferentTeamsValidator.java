package pl.sda.matchbetapp.api.validator;


import pl.sda.matchbetapp.api.model.Match;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DifferentTeamsValidator implements ConstraintValidator<DifferentTeams, Match> {

    @Override
    public boolean isValid(Match match, ConstraintValidatorContext constraintValidatorContext) {
        return !match.getFirstTeam().equalsIgnoreCase(match.getSecondTeam());
    }
}

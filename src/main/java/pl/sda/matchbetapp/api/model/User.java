package pl.sda.matchbetapp.api.model;

import lombok.*;
import pl.sda.matchbetapp.api.validator.UserNameLength;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@UserNameLength(minLength = 10,maxLength = 30)
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    @Email
    private String login;
}

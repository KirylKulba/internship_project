package pl.internship.antologic.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.internship.antologic.user.entity.UserRole;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto {

    private final UUID uuid;
    @EqualsAndHashCode.Include
    private final String login;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String email;
    private final BigDecimal cost;
    private final UserRole userRole;
}

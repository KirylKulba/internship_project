package pl.internship.antologic.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserForm {
    @NotBlank(message = "Login cannot be null or blank")
    @Size(min = 1, max = 50, message = "Login must contain between 1 and 50 letters")
    private String login;

    @NotBlank(message = "First name cannot be null or blank")
    @Size(min = 1, max = 50, message = "First name must contain between 1 and 50 letters")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or blank")
    @Size(min = 1, max = 50, message = "Last name must contain between 1 and 50 letters")
    private String lastName;

    @NotBlank(message = "Password cannot be null or blank")
    @Size(min = 1, max = 50, message = "Password must contain between 1 and 50 letters")
    private String password;

    @NotBlank(message = "Email cannot be null or blank")
    @Size(min = 1, max = 50, message = "Email must contain between 1 and 50 letters")
    @Email(message = "Email is not in valid email format")
    private String email;

    @NotNull(message = "Cost cannot be null")
    @Positive(message = "Cost must be positive")
    private BigDecimal cost;

    @NotBlank(message = "User role cannot be null or blank")
    @Pattern(regexp = "ADMIN|MANAGER|EMPLOYEE",message="Allowed values for user are ADMIN, MANAGER, EMPLOYEE")
    private String userRole;
}

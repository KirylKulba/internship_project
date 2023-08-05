package pl.internship.antologic.project.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
public class UpdateProjectForm {

    @Size(min = 1, max = 50, message = "Name must contain between 1 and 50 letters")
    private String name;

    private String description;

    private LocalDate beginDate;

    private LocalDate endDate;

    @Positive(message = "Cost must be positive")
    private BigDecimal budget;
}

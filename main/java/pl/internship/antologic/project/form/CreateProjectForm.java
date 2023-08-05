package pl.internship.antologic.project.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectForm {

    @NotBlank(message = "Name cannot be null or blank")
    @Size(min = 1, max = 50, message = "Name must contain between 1 and 50 letters")
    private String name;

    private String description;

    @NotNull
    private LocalDate beginDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Positive(message = "Cost must be positive")
    private BigDecimal budget;

    private List<UUID> users = new ArrayList<>();
}

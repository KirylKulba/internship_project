package pl.internship.antologic.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.internship.antologic.user.dto.UserDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ProjectDto {
    private final UUID uuid;
    private final String name;
    private final String description;
    private final LocalDate beginDate;
    private final LocalDate endDate;
    private final BigDecimal budget;
    private final BigDecimal budgetUse;
    private final Set<UserDto> users;
}

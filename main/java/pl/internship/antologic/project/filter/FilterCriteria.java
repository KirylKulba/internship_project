package pl.internship.antologic.project.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class FilterCriteria {
    private String name;

    private LocalDate from;
    private LocalDate to;

    private List<UUID> users;

    private BudgetOveruse budgetOveruse;

}

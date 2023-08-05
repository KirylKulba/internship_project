package pl.internship.antologic.user.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class FilterForm {
    private String login;
    private String firstName;
    private String lastName;
    private BigDecimal minCost;
    private BigDecimal maxCost;
    private String userRole;
}

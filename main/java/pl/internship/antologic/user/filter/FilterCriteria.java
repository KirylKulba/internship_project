package pl.internship.antologic.user.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterCriteria {
    private String login;
    private String firstName;
    private String lastName;
    private String userRole;
    private Double from;
    private Double to;
}

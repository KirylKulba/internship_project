package pl.internship.antologic.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectReportDto {
    private BigDecimal overallCost;
    private int overallHours;
    private boolean isBudgetOverused;
}

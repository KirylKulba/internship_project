package pl.internship.antologic.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.internship.antologic.report.utils.ReportStats;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReportDto{
    private BigDecimal overallCost;
    private int overallHours;
    private List<ReportStats> projects;
}

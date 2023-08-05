package pl.internship.antologic.report.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProjectSummary {
    private BigDecimal overallCost;
    private int hours;

    public final static ProjectSummary INITIAL_SUMMARY = getZeroInstance();

    public ProjectSummary(final ReportStats stat){
        this.overallCost = stat.getWorkCost();
        this.hours = stat.getHours();
    }

    public ProjectSummary add(final ProjectSummary summary){
        this.overallCost = overallCost.add(summary.getOverallCost());
        this.hours += summary.getHours();
        return this;
    }

    private static ProjectSummary getZeroInstance(){
        return new ProjectSummary(BigDecimal.ZERO, 0);
    }
}

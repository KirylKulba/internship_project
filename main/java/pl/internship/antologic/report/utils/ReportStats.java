package pl.internship.antologic.report.utils;

import java.math.BigDecimal;

public interface ReportStats {
    String getLogin();
    String getName();
    BigDecimal getWorkCost();
    int getHours();
}

package pl.internship.antologic.report.service;

import pl.internship.antologic.report.dto.UserReportDto;
import pl.internship.antologic.report.form.ReportForm;

import java.util.UUID;

public interface ReportService {
    UserReportDto getUserReport(final UUID userUuid, final ReportForm reportForm);
}

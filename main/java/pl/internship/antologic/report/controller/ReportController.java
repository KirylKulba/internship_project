package pl.internship.antologic.report.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.internship.antologic.report.dto.UserReportDto;
import pl.internship.antologic.report.form.ReportForm;
import pl.internship.antologic.report.service.ReportService;

import java.util.UUID;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping
    public UserReportDto getReport(@RequestParam final UUID currentUserUuid, @RequestBody final ReportForm reportForm){
        return this.reportService.getUserReport(currentUserUuid, reportForm);
    }

}

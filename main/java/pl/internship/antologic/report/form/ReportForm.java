package pl.internship.antologic.report.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.internship.antologic.report.service.ReportDateRangeType;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportForm {
    @NotNull(message="Report should be generated for a concrete user")
    private UUID userUuid;

    @NotNull(message="Report type must be provided")
    private ReportDateRangeType reportDateRangeType;
}

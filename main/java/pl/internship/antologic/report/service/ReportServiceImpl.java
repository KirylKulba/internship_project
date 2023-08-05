package pl.internship.antologic.report.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.internship.antologic.common.error.NoRightsException;
import pl.internship.antologic.common.error.NotFoundException;
import pl.internship.antologic.common.utils.DateUtils;
import pl.internship.antologic.report.dto.UserReportDto;
import pl.internship.antologic.report.form.DateRange;
import pl.internship.antologic.report.form.ReportForm;
import pl.internship.antologic.report.utils.ProjectSummary;
import pl.internship.antologic.report.utils.ReportStats;
import pl.internship.antologic.user.entity.User;
import pl.internship.antologic.user.entity.UserRole;
import pl.internship.antologic.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private final UserRepository userRepository;

    @Override
    public UserReportDto getUserReport(final UUID currentUserUuid, final ReportForm reportForm){
        final User user = findUserByUuid(currentUserUuid);
        checkIfUserHasPermissions(user, UserRole.recordRequiredRoles);

        final DateRange dateRange = DateUtils.getDateRange(reportForm.getReportDateRangeType());
        final List<ReportStats> reportStats = findUserByUuidAndRecordsDateRange(reportForm.getUserUuid(), dateRange.getStartDate(), dateRange.getEndDate());

        final UserReportDto report = new UserReportDto();

        final ProjectSummary summary = reportStats.stream()
                .map(ProjectSummary::new)
                .reduce(ProjectSummary.INITIAL_SUMMARY, ProjectSummary::add);

        report.setOverallHours(summary.getHours());
        report.setOverallCost(summary.getOverallCost());
        report.setProjects(reportStats);

        return report;
    }

    private User findUserByUuid(final UUID uuid){
        return userRepository.
                findUserByUuid(uuid).
                orElseThrow(()-> new NotFoundException("User not found"));
    }

    private void checkIfUserHasPermissions(final User user, final Set<UserRole> requiredRoles){
        final UserRole userRole =  user.getUserRole();
        if(!requiredRoles.contains(userRole)) {throw new NoRightsException("User has no permissions");}
    }

    private List<ReportStats> findUserByUuidAndRecordsDateRange(final UUID uuid, final LocalDate beginDate, final LocalDate endDate){
        return userRepository
                .findUserStats(uuid, beginDate.atStartOfDay(), endDate.atStartOfDay());
    }

}

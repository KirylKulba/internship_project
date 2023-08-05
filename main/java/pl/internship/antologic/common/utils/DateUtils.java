package pl.internship.antologic.common.utils;

import pl.internship.antologic.report.form.DateRange;
import pl.internship.antologic.report.service.ReportDateRangeType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {

    public static boolean validateIfEndIsAfterBegin(final LocalDate beginDate, final LocalDate endDate){
        return beginDate.isAfter(endDate);
    }

    public static boolean validateIfEndIsAfterBegin(final LocalDateTime beginTime, final LocalDateTime endTime){
        return beginTime.isAfter(endTime);
    }

    public static double getDiff(final LocalDateTime beginTime, final LocalDateTime endTime){
        return ((double) ChronoUnit.MINUTES.between(beginTime, endTime)/ 60L);
    }

    public static DateRange getDateRange(final ReportDateRangeType rangeType){

        final LocalDate now = LocalDate.now();

        final DateRange dateRange = new DateRange();

        switch (rangeType) {
            case THIS_WEEK -> {
                final LocalDate weekStartDate = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                dateRange.setStartDate(weekStartDate);
                dateRange.setEndDate(now);
            }
            case THIS_MONTH -> {
                final LocalDate monthStartDate = now.with(TemporalAdjusters.firstDayOfMonth());
                dateRange.setStartDate(monthStartDate);
                dateRange.setEndDate(now);
            }
            case THIS_YEAR -> {
                final LocalDate yearStartDate = now.with(TemporalAdjusters.firstDayOfYear());
                dateRange.setStartDate(yearStartDate);
                dateRange.setEndDate(now);
            }
            case ALL_TIME -> {
                dateRange.setStartDate(now.minusDays(ChronoUnit.CENTURIES.getDuration().toDays()));
                dateRange.setEndDate(now);
            }
        }

        return dateRange;

    }
}

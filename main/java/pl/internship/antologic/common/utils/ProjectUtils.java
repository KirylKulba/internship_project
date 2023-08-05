package pl.internship.antologic.common.utils;

import pl.internship.antologic.project.entity.Project;
import pl.internship.antologic.user.entity.User;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProjectUtils {

    public static final int HUNDRED = 100;

    public static void evaluateBudgetUsePercentage(final Project project) {
        BigDecimal budgetActual = BigDecimal.ZERO;
        for (final User user : project.getUsers()) {
            final Double hours = user.getRecords().stream()
                    .filter(record -> project.getRecords().contains(record))
                    .map(record -> DateUtils.getDiff(record.getBeginTimestamp(),record.getEndTimestamp()))
                    .reduce((double) 0, Double::sum);

            final BigDecimal userCost = user.getCost().multiply(BigDecimal.valueOf(hours));

            budgetActual = budgetActual.add(userCost);
        }

        project.setBudgetUse(budgetActual.divide(project.getBudget(), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(HUNDRED)));
    }


    public static void update(final Project source, final Project dest) {
        dest.setName(source.getName()==null ? dest.getName() : source.getName());
        dest.setDescription(source.getDescription()==null ? dest.getDescription() : source.getDescription());
        dest.setBeginDate(source.getBeginDate()==null ? dest.getBeginDate() : source.getBeginDate());
        dest.setEndDate(source.getEndDate()==null ? dest.getEndDate() : source.getEndDate());
        dest.setBudget(source.getBudget()==null ? dest.getBudget() : source.getBudget());
    }
}

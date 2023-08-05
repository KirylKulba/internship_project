package pl.internship.antologic.common.utils;

import pl.internship.antologic.project.entity.Project;
import pl.internship.antologic.project.filter.BudgetOveruse;
import pl.internship.antologic.user.entity.User;
import pl.internship.antologic.user.entity.UserRole;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SpecificationUtils {

    public static void addLikePredicate(final CriteriaBuilder criteriaBuilder, final String value, Path<String> path, final List<Predicate> predicateList) {
        if (Objects.nonNull(value)) {
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(path), "%" + value.toLowerCase() + "%"));
        }
    }

    public static void addFromToPredicate(final CriteriaBuilder criteriaBuilder,
                              final LocalDate beginDate,
                              final LocalDate endDate,
                              final Path<LocalDate> pathBeginDate,
                              final Path<LocalDate> pathEndDate,
                              final List<Predicate> predicateList) {

        if(Objects.nonNull(beginDate) && Objects.nonNull(endDate)){
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(pathBeginDate, endDate ));
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(pathEndDate, beginDate ));
        }else if(Objects.nonNull(beginDate)){
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(pathBeginDate, beginDate ));
        }else if(Objects.nonNull(endDate)){
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(pathEndDate, endDate ));
        }

    }

    public static void addProjectByUsersPredicate(final List<UUID> usersUuids, final Root<Project> root, final List<Predicate> predicateList) {
        if (Objects.nonNull(usersUuids)) {
            predicateList.add(root.join(Project.Fields.users).get(User.Fields.uuid).in(usersUuids));
        }
    }

    public static void isEqual(final CriteriaBuilder criteriaBuilder, final String value, final Root<User> root, final String field, final List<Predicate> predicateList) {
        if (Objects.nonNull(value)) {
            predicateList.add(criteriaBuilder.equal(root.get(field),UserRole.valueOf(value)));
        }
    }

    public static void isLike(final CriteriaBuilder criteriaBuilder, final String value, final Root<User> root, final String field, final List<Predicate> predicateList) {
        if (Objects.nonNull(value)) {
            predicateList.add(criteriaBuilder.like(root.get(field), "%" + value + "%"));
        }
    }

    public static void lessThan(final CriteriaBuilder criteriaBuilder, final Double value, final Root<User> root, final String field, final List<Predicate> predicateList) {
        if (Objects.nonNull(value)) {
            predicateList.add(criteriaBuilder.lessThan(root.get(field), BigDecimal.valueOf( value) ));
        }
    }

    public static void greaterThan(final CriteriaBuilder criteriaBuilder, final Double value, final Root<User> root, final String field, final List<Predicate> predicateList) {
        if (Objects.nonNull(value)) {
            predicateList.add(criteriaBuilder.greaterThan(root.get(field), BigDecimal.valueOf((Double) value) ));
        }
    }

    public static void budgetOverused(final CriteriaBuilder criteriaBuilder, final BudgetOveruse value, final Root<Project> root, final String field, final List<Predicate> predicateList) {
        if (Objects.nonNull(value)) {
            if(value.equals(BudgetOveruse.YES)){
                predicateList.add(criteriaBuilder.greaterThan(root.get(field), BigDecimal.valueOf(ProjectUtils.HUNDRED) ));
            }else{
                predicateList.add(criteriaBuilder.lessThan(root.get(field),BigDecimal.valueOf(ProjectUtils.HUNDRED) ));
            }

        }
    }

}

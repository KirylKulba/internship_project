package pl.internship.antologic.project.filter;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import pl.internship.antologic.common.utils.SpecificationUtils;
import pl.internship.antologic.project.entity.Project;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class ProjectSpecification implements Specification<Project> {
    private final FilterCriteria filter;

    @Override
    public Predicate toPredicate(final Root<Project> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        final List<Predicate> predicateList = new ArrayList<>();

        SpecificationUtils.addLikePredicate(builder, filter.getName(), root.get(Project.Fields.name), predicateList);
        SpecificationUtils.addFromToPredicate(builder, filter.getFrom(), filter.getTo(), root.get(Project.Fields.beginDate), root.get(Project.Fields.endDate) , predicateList);
        SpecificationUtils.addProjectByUsersPredicate(filter.getUsers(), root, predicateList);
        SpecificationUtils.budgetOverused(builder, filter.getBudgetOveruse(), root, Project.Fields.budgetUse, predicateList);

        query.distinct(true);
        return builder.and(predicateList.toArray(Predicate[]::new));
    }
}

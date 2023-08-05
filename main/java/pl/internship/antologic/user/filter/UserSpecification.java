package pl.internship.antologic.user.filter;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import pl.internship.antologic.common.utils.SpecificationUtils;
import pl.internship.antologic.user.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UserSpecification implements Specification<User> {

    private final FilterCriteria filter;

    @Override
    public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        final List<Predicate> predicateList = new ArrayList<>();

        SpecificationUtils.isEqual(builder, filter.getUserRole(), root, User.Fields.userRole, predicateList);
        SpecificationUtils.isLike(builder, filter.getFirstName(), root, User.Fields.firstName, predicateList);
        SpecificationUtils.isLike(builder, filter.getLastName(), root, User.Fields.lastName, predicateList);
        SpecificationUtils.isLike(builder, filter.getLogin(), root, User.Fields.login, predicateList);
        SpecificationUtils.greaterThan(builder, filter.getFrom(), root, User.Fields.cost, predicateList);
        SpecificationUtils.lessThan(builder, filter.getTo(), root, User.Fields.cost, predicateList);


        return builder.and(predicateList.toArray(Predicate[]::new));
    }

}

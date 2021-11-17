package bg.wallet.www.project.repositories;

import bg.wallet.www.project.models.Category;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CategorySearchSpecification implements Specification<Category> {

    private String name;

    public CategorySearchSpecification(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate p = criteriaBuilder.conjunction();

        if (name != null) {
            p.getExpressions().add(criteriaBuilder.and(criteriaBuilder.equal(root.get("name"), name)));
        }

        return p;
    }
}

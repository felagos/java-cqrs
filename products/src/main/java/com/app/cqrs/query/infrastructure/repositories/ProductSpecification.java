package com.app.cqrs.query.infrastructure.repositories;

import org.springframework.data.jpa.domain.Specification;
import com.app.cqrs.query.domain.ProductFilter;
import com.app.cqrs.query.infrastructure.entities.ProductEntity;

public class ProductSpecification {

    public static Specification<ProductEntity> whereLikeTitle(ProductFilter filter) {
        return (root, query, criteriaBuilder) -> {
            System.out.println("Filter Title: " + filter.getTitle());
            if (!filter.hasTitle()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("title"), "%" + filter.getTitle() + "%");
        };
    }

    public static Specification<ProductEntity> whereMinPrice(ProductFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (!filter.hasMinPrice()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice());
        };
    }

    public static Specification<ProductEntity> whereMaxPrice(ProductFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (!filter.hasMaxPrice()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice());
        };
    }

}

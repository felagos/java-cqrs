package com.app.cqrs.query.infrastructure.repositories;

import java.util.regex.Pattern;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import com.app.cqrs.query.domain.ProductFilter;

public class ProductSpecification {

    public static Query buildQuery(ProductFilter filter) {
        Query query = new Query();

        if (filter.hasTitle()) {
            // case-insensitive contains; use Pattern.quote to escape user input
            String quoted = Pattern.quote(filter.getTitle());
            query.addCriteria(Criteria.where("title").regex(".*" + quoted + ".*", "i"));
        }

        if (filter.hasMinPrice()) {
            query.addCriteria(Criteria.where("price").gte(filter.getMinPrice()));
        }

        if (filter.hasMaxPrice()) {
            query.addCriteria(Criteria.where("price").lte(filter.getMaxPrice()));
        }

        return query;
    }

}

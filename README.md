# Jpa-Specifications-Example
This project provides practical examples of using JPA Specifications for dynamic querying in Spring Data JPA.
![image](https://github.com/AhmetAksunger/Jpa-Specifications-Example/assets/116587797/d0aed4f9-98d6-4a39-a0ea-c83faaaa4cde)
## 📖 Introduction
JPA Specifications provide a powerful way to dynamically build queries for database operations.<br/>
It allows you to create Predicate Queries, that can be re-used, and can be combined with other predicates easily.<br/>
It's mainly used for filtering operations, which is what I showed in my repository.<br/>

<p>This repository shows an example of how to use Jpa Specifications to filter list requests.</p>

<table>
  <tr>
    <th>Method</th>
    <th>URL</th>
    <th>Http Status</th>
  </tr>
    <tr>
    <td>GET</td>
    <td>/api/v1/products</td>
    <td>200 OK</td>
  </tr>
</table>

### Get Products
All fields are optional, and are being filtered with help of Specifications.
Specifications help you combine the entered fields, and serve a result based on the specified filters.
Otherwise, you would have to write queries for each combination of filters.
```json
{
    "filter":{
        "keyword":null,
        "name":null,
        "minPrice":null,
        "maxPrice":null,
        "categoryIds":[],
        "date":{
          "createdAt": null,
          "dateComparison": null // AT,BEFORE,AFTER
        }
    }
}
```

The fields are being converted to a specification with the following method.
The non-null fields are combined with the 'and' boolean operator.
```java
    @Override
    public Specification<Product> toSpecification() {
        Specification<Product> specification = Specification.where(null);

        if (filter.keyword != null) {
            specification = specification.and(ProductSpecification.search(filter.keyword));
        }

        if (filter.name != null) {
            specification = specification.and(ProductSpecification.hasName(filter.name));
        }

        if (filter.minPrice != null) {
            specification = specification.and(ProductSpecification.hasPriceGreaterThanOrEqualTo(filter.minPrice));
        }

        if (filter.maxPrice != null) {
            specification = specification.and(ProductSpecification.hasPriceLessThanOrEqualTo(filter.maxPrice));
        }

        if (!CollectionUtils.isEmpty(filter.categoryIds)) {
            specification = specification.and(ProductSpecification.hasCategoryIds(filter.categoryIds.stream().toList()));
        }

        if (filter.date != null && filter.date.createdAt != null && filter.date.dateComparison != null) {
            Date date = filter.date.createdAt;
            Filter.CreationDate.DateComparison dateComparison = filter.date.dateComparison;

            specification = switch (dateComparison) {
                case AT -> specification.and(ProductSpecification.createdAt(date));
                case AFTER -> specification.and(ProductSpecification.createdAfterThan(date));
                case BEFORE -> specification.and(ProductSpecification.createdBeforeThan(date));
            };
        }

        return specification;
    }
```

Here are the ```ProductSpecification``` methods that are used on the above method:
```java
public class ProductSpecification {

    private ProductSpecification() {
    }


    // #### EQUALITY
    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<Product> createdAt(Date date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("createdAt"), date);
    }

    public static Specification<Product> hasNoName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("name"), name);
    }
    // ####

    // #### STRINGS
    public static Specification<Product> hasNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }
    // ###

    // ### COMPARISONS
    public static Specification<Product> hasPriceGreaterThanOrEqualTo(BigDecimal price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> hasPriceLessThanOrEqualTo(BigDecimal price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("name"), price);
    }

    public static Specification<Product> hasPriceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), min, max);
    }

    public static Specification<Product> createdBeforeThan(Date date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("createdAt"), date);
    }

    public static Specification<Product> createdAfterThan(Date date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("createdAt"), date);
    }

    public static Specification<Product> updatedBeforeThan(Date date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("updatedAt"), date);
    }

    public static Specification<Product> updatedAfterThan(Date date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("updatedAt"), date);
    }
    // ###

    // ### JOINS
    public static Specification<Product> hasCategoryNameLike(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Category> categoryJoin = root.join("categories");

            return criteriaBuilder.like(categoryJoin.get("name"), categoryName);
        };
    }

    public static Specification<Product> hasCategoryIds(Collection<Long> categoryIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, Category> categoryJoin = root.join("categories");

            return categoryJoin.get("id").in(categoryIds);
        };
    }
    // ###

    // ### BOOLEANS
    public static Specification<Product> search(String keyword) {
        return (root, query, criteriaBuilder) -> {

            Expression<String> nameLowercase = criteriaBuilder.lower(root.get("name"));
            Expression<String> companyNameLowercase = criteriaBuilder.lower(root.get("companyName"));

            return criteriaBuilder.or(
                    criteriaBuilder.like(nameLowercase, "%" + keyword.toLowerCase() + "%"),
                    criteriaBuilder.like(companyNameLowercase, "%" + keyword.toLowerCase() + "%")
            );
        };
    }
    // ###
}
```

## 🛠️ Technologies
- Java 17
- Spring Boot 3.0
- Spring Data JPA
- Restful API
- Lombok
- Maven
- DataFaker

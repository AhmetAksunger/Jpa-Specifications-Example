package com.ahmetaksunger.jpaspecifications.config;

import com.ahmetaksunger.jpaspecifications.entity.Category;
import com.ahmetaksunger.jpaspecifications.entity.Product;
import com.ahmetaksunger.jpaspecifications.repository.CategoryRepository;
import com.ahmetaksunger.jpaspecifications.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class InitialDataConfig implements CommandLineRunner {

    private final Faker faker = new Faker();
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private Set<Category> generateFakeCategory(int amount) {
        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            Category category = Category.builder()
                    .name(faker.commerce().department())
                    .build();
            categoryRepository.save(category);
            categories.add(category);
        }
        return categories;
    }

    private void generateFakeProduct(int amount) {
        for (int i = 0; i < amount; i++) {
            Product product = Product.builder()
                    .companyName(faker.commerce().vendor())
                    .name(faker.commerce().productName())
                    .price(BigDecimal.valueOf(Double.parseDouble(faker.commerce().price())))
                    .description(faker.lorem().characters(10, 100))
                    .stockQuantity(faker.number().numberBetween(5, 20))
                    .categories(generateFakeCategory(faker.number().numberBetween(2, 6)))
                    .build();
            productRepository.save(product);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        generateFakeProduct(200);
    }
}

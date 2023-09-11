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
import java.util.List;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class InitialDataConfig implements CommandLineRunner {

    private final Faker faker = new Faker();
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private void generateFakeCategories(int amount) {
        for (int i = 0; i < amount; i++) {
            Category category = Category.builder()
                    .name(faker.commerce().department())
                    .build();
            categoryRepository.save(category);
        }
    }

    private Set<Category> randomlyChooseCategories() {
        List<Category> categories = categoryRepository.findAll();
        Set<Category> chosenCategories = new HashSet<>();

        int numCategoriesToAdd = faker.number().numberBetween(2, 10);

        while (chosenCategories.size() < numCategoriesToAdd) {
            Category chosenCategory = categories.get(faker.number().numberBetween(0, categories.size()));
            chosenCategories.add(chosenCategory);
        }

        return chosenCategories;
    }

    private void generateFakeProducts(int amount) {

        for (int i = 0; i < amount; i++) {
            Product product = Product.builder()
                    .companyName(faker.commerce().vendor())
                    .name(faker.commerce().productName())
                    .price(BigDecimal.valueOf(Double.parseDouble(faker.commerce().price())))
                    .description(faker.lorem().characters(10, 100))
                    .stockQuantity(faker.number().numberBetween(5, 20))
                    .categories(randomlyChooseCategories())
                    .build();
            productRepository.save(product);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        generateFakeCategories(100);
        generateFakeProducts(500);
        log.info("GENERATED FAKE DATA");
    }
}

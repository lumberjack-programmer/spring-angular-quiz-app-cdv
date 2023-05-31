package com.lumberjack.quizappbackend.service.quiz.impl;

import com.lumberjack.quizappbackend.model.quiz.Category;
import com.lumberjack.quizappbackend.service.quiz.QuizCategoryService;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class QuizCategoryServiceImpl implements QuizCategoryService {
    private final MongoTemplate mongoTemplate;
    private static final String CATEGORY_COLLECTION_NAME = "categories";

    @Autowired
    public QuizCategoryServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Category> getAllCategories() {
        log.info("Getting all categories");
        return mongoTemplate.findAll(Category.class);
    }

    @Override
    public Category getCategoryById(String id) {
        log.info("Getting category by id: {}", id);
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Category.class);
    }

    @Override
    public Category findCategoryByName(String categoryName) {
        log.info("Getting category by name: {}", categoryName);
        Query query = new Query(Criteria.where("categoryName").is(categoryName));
        return mongoTemplate.findOne(query, Category.class);
    }

    @Override
    public boolean checkIfNull(Category category) {
        log.info("Getting category: {}", category);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(category.getId()));
        query.addCriteria(Criteria.where("categoryName").is(category.getCategoryName()));
        return Optional.of(mongoTemplate.findOne(query, Category.class)).isPresent();
    }

    @Override
    public Category saveCategory(Category category) {
        log.info("Adding new category: {}", category);
        Category categoryByName = findCategoryByName(category.getCategoryName());
        if (categoryByName != null) {
            categoryByName.setCategoryName(categoryByName.getCategoryName());
            return mongoTemplate.save(categoryByName, CATEGORY_COLLECTION_NAME);
        } else {
            return mongoTemplate.save(category, CATEGORY_COLLECTION_NAME);
        }
    }

    @Override
    public boolean deleteCategoryById(String id) {
        log.info("Deleting category by id: {}", id);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        DeleteResult categoryRemoved = mongoTemplate.remove(query, Category.class);
        return categoryRemoved.wasAcknowledged();
    }

    @Override
    public boolean deleteCategory(Category category) {
        log.info("Deleting category: {}", category);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(category.getId()));
        query.addCriteria(Criteria.where("categoryName").is(category.getCategoryName()));
        DeleteResult categoryRemoved = mongoTemplate.remove(query, Category.class);
        return categoryRemoved.wasAcknowledged();
    }

    @Override
    public boolean deleteCategoryByName(String categoryName) {
        log.info("Deleting category by name: {}", categoryName);
        Query query = new Query();
        query.addCriteria(Criteria.where("categoryName").is(categoryName));
        DeleteResult categoryRemoved = mongoTemplate.remove(query, Category.class);
        return categoryRemoved.wasAcknowledged();
    }

    @Override
    public List<Category> searchCategories(String searchTerm) {
        return null;
    }
}

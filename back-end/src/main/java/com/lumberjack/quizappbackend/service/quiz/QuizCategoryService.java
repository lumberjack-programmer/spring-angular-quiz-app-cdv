package com.lumberjack.quizappbackend.service.quiz;

import com.lumberjack.quizappbackend.model.quiz.Category;

import java.util.List;
import java.util.Optional;

public interface QuizCategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(String id);
    Category findCategoryByName(String categoryName);
    boolean checkIfNull(Category category);
    Category saveCategory(Category category);
    boolean deleteCategoryById(String id);
    boolean deleteCategoryByName(String categoryName);
    boolean deleteCategory(Category category);
    List<Category> searchCategories(String searchTerm);
}

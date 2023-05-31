package com.lumberjack.quizappbackend.controller;

import com.lumberjack.quizappbackend.model.quiz.Category;
import com.lumberjack.quizappbackend.service.quiz.QuizCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final QuizCategoryService quizCategoryService;

    @Autowired
    public CategoryController(QuizCategoryService quizCategoryService) {
        this.quizCategoryService = quizCategoryService;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(quizCategoryService.getAllCategories(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") String id) {
        Category category = quizCategoryService.getCategoryById(id);

        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/getByName/{name}", method = RequestMethod.GET)
    public ResponseEntity<Category> findCategoryByName(@PathVariable("name") String name) {
        Category category = quizCategoryService.findCategoryByName(name);

        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        Category categorySaved = quizCategoryService.saveCategory(category);

        if (categorySaved != null) {
            return new ResponseEntity<>(categorySaved, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteCategoryById(@PathVariable("id") String id) {
        boolean result = quizCategoryService.deleteCategoryById(id);

        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/deleteByName/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteCategoryByName(@PathVariable("name") String name) {
        boolean result = quizCategoryService.deleteCategoryByName(name);

        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteCategoryByName(@RequestBody Category category) {
        boolean result = quizCategoryService.deleteCategory(category);

        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}

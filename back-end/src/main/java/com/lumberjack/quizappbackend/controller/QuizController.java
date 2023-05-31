package com.lumberjack.quizappbackend.controller;

import com.lumberjack.quizappbackend.model.quiz.Quiz;
import com.lumberjack.quizappbackend.service.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200/**", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @RequestMapping(value = "/getAllQuizzess", method = RequestMethod.GET)
    public ResponseEntity<?> getAllQuizzes() {
        return new ResponseEntity<>(quizService.getAllQuizzes(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getQuizById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getQuizById(@PathVariable String id) {
        return new ResponseEntity<>(quizService.getQuizById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/findQuizByTitle/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> findQuizByTitle(@PathVariable String title) {
        return new ResponseEntity<>(quizService.findQuizByTitle(title), HttpStatus.OK);
    }

    @RequestMapping(value = "/findQuizByCategoryName/{category}", method = RequestMethod.GET)
    public ResponseEntity<?> findQuizByCategoryName(@PathVariable String category) {
        return new ResponseEntity<>(quizService.findQuizByCategoryName(category), HttpStatus.OK);
    }

    @RequestMapping(value = "/saveQuiz", method = RequestMethod.POST)
    public ResponseEntity<?> saveQuiz(@RequestBody Quiz quiz) {
        return new ResponseEntity<>(quizService.saveQuiz(quiz), HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteQuizById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteQuizById(@PathVariable String id) {
        return new ResponseEntity<>(quizService.deleteQuizById(id), HttpStatus.OK);
    }
}

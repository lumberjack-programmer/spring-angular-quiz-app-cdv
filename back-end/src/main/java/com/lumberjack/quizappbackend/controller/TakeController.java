package com.lumberjack.quizappbackend.controller;

import com.lumberjack.quizappbackend.model.quiz.Take;
import com.lumberjack.quizappbackend.service.quiz.QuizTakeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200/**", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/take")
@Slf4j
public class TakeController {

    private final QuizTakeService takeService;

    @Autowired
    public TakeController(QuizTakeService takeService) {
        this.takeService = takeService;
    }

    @RequestMapping(value = "/getAllTakes", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTakes() {
        log.info("getAllTakes");
        return new ResponseEntity<>(takeService.getAllTakes(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getTakeById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTakeById(@PathVariable String id) {
        return new ResponseEntity<>(takeService.getTakeById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/getTakeByUsername/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> findTakeByUsername(@PathVariable String username) {
        return new ResponseEntity<>(takeService.findTakeByUsername(username), HttpStatus.OK);
    }

    @RequestMapping(value = "/findTakeByCategoryName/{category}", method = RequestMethod.GET)
    public ResponseEntity<?> findTakeByCategoryName(@PathVariable String category) {
        return new ResponseEntity<>(takeService.findTakeByCategoryName(category), HttpStatus.OK);
    }

    @RequestMapping(value = "/saveTake", method = RequestMethod.POST)
    public ResponseEntity<?> saveQuiz(@RequestBody Take take) {
        return new ResponseEntity<>(takeService.saveTake(take), HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteTakeById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> deleteTakeById(@PathVariable String id) {
        return new ResponseEntity<>(takeService.deleteTake(id), HttpStatus.OK);
    }
}

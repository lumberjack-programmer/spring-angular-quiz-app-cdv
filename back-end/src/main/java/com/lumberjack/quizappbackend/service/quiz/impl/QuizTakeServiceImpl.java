package com.lumberjack.quizappbackend.service.quiz.impl;

import com.lumberjack.quizappbackend.model.quiz.Question;
import com.lumberjack.quizappbackend.model.quiz.Quiz;
import com.lumberjack.quizappbackend.model.quiz.Take;
import com.lumberjack.quizappbackend.model.quiz.UserQuestionAnswer;
import com.lumberjack.quizappbackend.service.quiz.QuizTakeService;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class QuizTakeServiceImpl implements QuizTakeService {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public QuizTakeServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Take> getAllTakes() {
        log.info("Getting all takes");
        return mongoTemplate.findAll(Take.class);
    }

    @Override
    public Take getTakeById(String id) {
        log.info("Getting take by id: {}", id);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Take.class);
    }

    @Override
    public Take findTakeByUsername(String username) {
        log.info("Getting take by username: {}", username);
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(username));
        return mongoTemplate.findOne(query, Take.class);
    }

    @Override
    public Take findTakeByCategoryName(String categoryName) {
        log.info("Getting take by categoryName: {}", categoryName);
        Query query = new Query();
        query.addCriteria(Criteria.where("quiz.category").is(categoryName));
        return mongoTemplate.findOne(query, Take.class);
    }

    @Override
    public Take findTakeByQuizId(String quizId) {
        log.info("Getting take by quiz id: {}", quizId);
        Query query = new Query();
        query.addCriteria(Criteria.where("quiz.id").is(quizId));
        return mongoTemplate.findOne(query, Take.class);
    }

    @Override
    public Take findTakeByQuiz(Quiz quiz) {
        log.info("Getting take by quiz: {}", quiz);
        Query query = new Query();
        query.addCriteria(Criteria.where("quiz.id").is(quiz.getId()));
        return mongoTemplate.findOne(query, Take.class);
    }

    @Override
    public Take saveTake(Take take) {
        log.info("Saving take by quiz: {}", take);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        take.setUserName(authentication.getPrincipal().toString());
        take = doCalculations(take);
        return mongoTemplate.save(take);
    }

    private Take doCalculations(Take take) {
        List<UserQuestionAnswer> userQuestionAnswers = take.getUserQuestionAnswers();
        take.setNumberCorrectAnswers(0);
        userQuestionAnswers.forEach(userQuestionAnswer -> {

            userQuestionAnswer.setResult(true);
            Question question = userQuestionAnswer.getQuestion();
            List<Integer> correctAnswers = question.getCorrectAnswers();
            List<Integer> userAnswers = userQuestionAnswer.getUserAnswers();
            if (correctAnswers.size() != userAnswers.size()) {
                userQuestionAnswer.setResult(false);
            } else {
                userAnswers.forEach(userAnswer -> {
                    if (!correctAnswers.contains(userAnswer)) {
                        userQuestionAnswer.setResult(false);
                    }
                });

                if (userQuestionAnswer.getResult().equals(true)) {
                    take.setNumberCorrectAnswers(take.getNumberCorrectAnswers() + 1);
                }
            }
        });
        take.setNumberIncorrectAnswers(take.getUserQuestionAnswers().size() - take.getNumberCorrectAnswers());
        take.setScore(Math.round(take.getNumberCorrectAnswers() * 100 / (double)take.getUserQuestionAnswers().size()));

        return take;
    }

    @Override
    public boolean deleteTake(String id) {
        log.info("Deleting take by id: {}", id);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        DeleteResult takeRemoved = mongoTemplate.remove(query, Take.class);
        return takeRemoved.wasAcknowledged();
    }

    @Override
    public List<Take> searchTakes(String searchTerm) {
        return null;
    }
}

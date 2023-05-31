package com.lumberjack.quizappbackend.service.quiz.impl;

import com.lumberjack.quizappbackend.model.quiz.Code;
import com.lumberjack.quizappbackend.model.quiz.Question;
import com.lumberjack.quizappbackend.model.quiz.Quiz;
import com.lumberjack.quizappbackend.service.quiz.QuizService;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public QuizServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        log.info("Getting all quizzes");
        return mongoTemplate.findAll(Quiz.class);
    }

    @Override
    public Quiz getQuizById(String id) {
        log.info("Getting quiz by id: {}", id);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Quiz.class);
    }

    @Override
    public Quiz findQuizByTitle(String quizTitle) {
        log.info("Getting quiz by name: {}", quizTitle);
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(quizTitle));
        return mongoTemplate.findOne(query, Quiz.class);
    }

    @Override
    public List<Quiz> findQuizByCategoryName(String categoryName) {
        log.info("Getting quiz by categoryName: {}", categoryName);
        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(categoryName));
        return mongoTemplate.find(query, Quiz.class);
    }

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        log.info("Saving quiz: {}", quiz);
        Quiz quizByTitle = findQuizByTitle(quiz.getTitle());
        if (quizByTitle != null) {
            quizByTitle.setCategory(quiz.getCategory());
            quizByTitle.setTimeLimit(quiz.getTimeLimit());
            return mongoTemplate.save(quizByTitle);
        } else {
            List<Question> questions = quiz.getQuestions();
            questions.forEach(question -> {
               List<Integer> correctOptionIds = new ArrayList<>();
               question.setOptions(Code.assignCode(question.getOptions()));

               question.getOptions().forEach(option -> {
                   if (option.isCorrectAnswer()) {
                       correctOptionIds.add(option.getOptionId());
                   }
               });
               question.setCorrectAnswers(correctOptionIds);
            });

            quiz.setCreatedAt(new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime()));
            return mongoTemplate.save(quiz);
        }
    }

    @Override
    public boolean deleteQuizById(String id) {
        log.info("Deleting quiz by id: {}", id);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        DeleteResult quizRemoved = mongoTemplate.remove(query, Quiz.class);
        return quizRemoved.wasAcknowledged();
    }

    @Override
    public List<Quiz> searchQuizzes(String searchTerm) {
        return null;
    }
}

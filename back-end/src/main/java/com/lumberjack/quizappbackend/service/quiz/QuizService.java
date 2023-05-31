package com.lumberjack.quizappbackend.service.quiz;

import com.lumberjack.quizappbackend.model.quiz.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {
    List<Quiz> getAllQuizzes();
    Quiz getQuizById(String id);
    Quiz findQuizByTitle(String quizTitle);
    List<Quiz> findQuizByCategoryName(String categoryName);
    Quiz saveQuiz(Quiz quiz);
    boolean deleteQuizById(String id);
    // TODO: Delete all quizzes
    List<Quiz> searchQuizzes(String searchTerm);
}

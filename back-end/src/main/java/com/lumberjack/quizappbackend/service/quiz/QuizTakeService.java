package com.lumberjack.quizappbackend.service.quiz;

import com.lumberjack.quizappbackend.model.quiz.Quiz;
import com.lumberjack.quizappbackend.model.quiz.Take;

import java.util.List;

public interface QuizTakeService {
    List<Take> getAllTakes();
    Take getTakeById(String id);
    Take findTakeByUsername(String username);
    Take findTakeByCategoryName(String category);
    Take findTakeByQuiz(Quiz quiz);
    Take findTakeByQuizId(String quizId);
    Take saveTake(Take take);
    boolean deleteTake(String id);
    // TODO: Delete all takes
    List<Take> searchTakes(String searchTerm);
}

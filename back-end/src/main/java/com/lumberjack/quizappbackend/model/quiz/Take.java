package com.lumberjack.quizappbackend.model.quiz;

import com.lumberjack.quizappbackend.model.quiz.dto.QuizDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "quizTakes")
public class Take implements Serializable {
    @Serial
    private static final long serialVersionUID = -4354354354543543552L;

    @Id
    private String id;
    private String userName;
    private QuizDto quiz;
    private String startTime;
    private String endTime;
    private List<UserQuestionAnswer> userQuestionAnswers;
    private double score;
    private int numberCorrectAnswers;
    private int numberIncorrectAnswers;
    private int timeTaken;

    public void setQuiz(Quiz quiz) {
        QuizDto quizDto = new QuizDto();
        quizDto.setId(quiz.getId());
        quizDto.setCategory(quiz.getCategory());
        quizDto.setTitle(quiz.getTitle());
        quizDto.setCreatedAt(quiz.getCreatedAt());
        quizDto.setTimeLimit(quiz.getTimeLimit());
        this.quiz = quizDto;
    }
}

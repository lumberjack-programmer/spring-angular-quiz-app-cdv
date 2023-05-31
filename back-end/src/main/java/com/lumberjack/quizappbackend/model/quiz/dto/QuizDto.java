package com.lumberjack.quizappbackend.model.quiz.dto;

import com.lumberjack.quizappbackend.model.quiz.TimeLimit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serial;
import java.io.Serializable;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class QuizDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -4354354354543543554L;

    private String id;
    private String title;
    private String category;
    private TimeLimit timeLimit;
    private String createdAt;
}

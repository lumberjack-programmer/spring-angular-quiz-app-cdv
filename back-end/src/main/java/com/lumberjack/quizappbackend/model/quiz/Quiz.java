package com.lumberjack.quizappbackend.model.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "quizzes")
public class Quiz implements Serializable {
    @Serial
    private static final long serialVersionUID = -4354354354543543551L;

    @Id
    private String id;
    private String title;
    private String category;
    private List<Question> questions = new ArrayList<>();
    private TimeLimit timeLimit;
    private String createdAt;
}

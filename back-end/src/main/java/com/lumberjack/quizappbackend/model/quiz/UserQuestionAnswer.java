package com.lumberjack.quizappbackend.model.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserQuestionAnswer implements Serializable {
    @Serial
    private static final long serialVersionUID = -4354354354543543553L;

    private Question question;
    private List<Integer> userAnswers;
    private Boolean result;
}

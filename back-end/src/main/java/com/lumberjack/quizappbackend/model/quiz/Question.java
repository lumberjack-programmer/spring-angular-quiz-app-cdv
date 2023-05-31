package com.lumberjack.quizappbackend.model.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Question implements Serializable {
    @Serial
    private static final long serialVersionUID = -4354354354543543550L;

    private String questionText;
    private String questionCode;
    private Set<Option> options = new HashSet<>();
    private List<Integer> correctAnswers;
    private String questionType;
}

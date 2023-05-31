package com.lumberjack.quizappbackend.model.quiz;

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
public class Option implements Serializable {
    @Serial
    private static final long serialVersionUID = -4354354354543543548L;

    private int optionId;
    private String textInput;
    private String codeInput;
    private boolean correctAnswer;
    private String image;
}

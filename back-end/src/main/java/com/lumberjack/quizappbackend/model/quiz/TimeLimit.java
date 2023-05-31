package com.lumberjack.quizappbackend.model.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TimeLimit {
    private boolean timeLimitEnabled;
    private String time;
}

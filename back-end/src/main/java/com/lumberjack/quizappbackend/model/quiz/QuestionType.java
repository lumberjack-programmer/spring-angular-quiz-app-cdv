package com.lumberjack.quizappbackend.model.quiz;

public enum QuestionType {
    MULTIPLE_CHOICE("MULTIPLE_CHOICE"),
    ONE_CHOICE("ONE_CHOICE"),
    TRUE_FALSE("TRUE_FALSE");
    public final String questionType;
    private QuestionType(String questionType) {
        this.questionType = questionType;
    }
}

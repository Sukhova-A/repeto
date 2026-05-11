package com.anastasiia.repeto.service;

import java.util.List;

import com.anastasiia.repeto.model.Answer;
import com.anastasiia.repeto.model.Question;
import com.anastasiia.repeto.model.QuestionType;

public class ValidatorService {

    public boolean questionWithAnswersValidate(Question question, List<Answer> answers) {
        if (answers.size() < 4) return false;
        int correctAnswers = answers.stream().filter(Answer::isCorrect).toList().size();
        if (correctAnswers < 1) return false;
        if (question.getQuestionType() == QuestionType.SINGLE_CHOICE && correctAnswers > 1) return false;
        return true;
    }
}

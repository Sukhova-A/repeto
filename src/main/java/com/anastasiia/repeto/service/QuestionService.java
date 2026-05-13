package com.anastasiia.repeto.service;

import java.sql.Connection;
import java.util.List;

import com.anastasiia.repeto.model.Answer;
import com.anastasiia.repeto.model.Question;
import com.anastasiia.repeto.model.QuestionType;
import com.anastasiia.repeto.repository.AnswerRepository;
import com.anastasiia.repeto.repository.DatabaseManager;
import com.anastasiia.repeto.repository.QuestionRepository;

public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ValidatorService validatorService;

    public QuestionService(QuestionRepository questionRepository,
                           AnswerRepository answerRepository,
                           ValidatorService validatorService) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.validatorService = validatorService;
    }

    public boolean createQuestionWIthAnswers(Question question, List<Answer> answers) {
        if (!validatorService.questionWithAnswersValidate(question, answers)) {
            return false;
        }
        System.out.println("Validation passed");

        try (Connection conn = DatabaseManager.getConnection()){
            conn.setAutoCommit(false);
            Question savedQuestion = questionRepository.save(conn, question);

            for (Answer answer : answers) {
                answer.setQuestionId(savedQuestion.getId());
                answerRepository.save(conn, answer);
            }

            conn.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public List<Question> loadQuestions(int offset, int pageSize) {
        try (Connection conn = DatabaseManager.getConnection()) {
            return questionRepository.loadPage(conn, offset, pageSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
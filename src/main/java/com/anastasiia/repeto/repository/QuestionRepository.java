package com.anastasiia.repeto.repository;

import java.sql.Connection;
import java.util.List;

import com.anastasiia.repeto.model.Question;

public interface QuestionRepository {

    Question save(Connection connection, Question question);
    List<Question> loadPage(Connection connection, int offset, int limit);
}
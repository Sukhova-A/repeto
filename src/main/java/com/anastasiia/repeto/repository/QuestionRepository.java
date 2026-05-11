package com.anastasiia.repeto.repository;

import java.sql.Connection;

import com.anastasiia.repeto.model.Question;

public interface QuestionRepository {

    Question save(Connection connection, Question question);
}
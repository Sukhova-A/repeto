package com.anastasiia.repeto.repository;

import java.sql.Connection;

import com.anastasiia.repeto.model.Answer;

public interface AnswerRepository {

    Answer save(Connection connection, Answer answer);
}
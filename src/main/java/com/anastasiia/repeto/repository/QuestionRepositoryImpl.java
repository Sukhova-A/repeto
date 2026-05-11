package com.anastasiia.repeto.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.anastasiia.repeto.model.Question;

public class QuestionRepositoryImpl implements QuestionRepository {

    private static final String INSERT_QUERY = "INSERT INTO questions(text, tag, type) VALUES(?,?,?)";

    @Override
    public Question save(Connection conn, Question question) {
        try(PreparedStatement st = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, question.getTag());
            st.setString(2, question.getText());
            st.setString(3, question.getQuestionType().name());

            int affectedRows = st.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating question failed");
            }

            try (ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) {
                    question.setId(keys.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return question;
    }
}
package com.anastasiia.repeto.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.anastasiia.repeto.model.Answer;

public class AnswerRepositoryImpl implements AnswerRepository {

    private static final String INSERT_QUERY = "INSERT INTO answers(question_id, answer, is_correct) VALUES(?,?,?)";

    @Override
    public Answer save(Connection conn, Answer answer) {
        try (PreparedStatement st = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            st.setLong(1, answer.getQuestionId());
            st.setString(2, answer.getAnswer());
            st.setBoolean(3, answer.isCorrect());

            int affectedRows = st.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating answer failed");
            }

            try (ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) {
                    answer.setId(keys.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return answer;
    }
}
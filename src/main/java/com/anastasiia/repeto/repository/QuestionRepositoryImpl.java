package com.anastasiia.repeto.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.anastasiia.repeto.model.Question;
import com.anastasiia.repeto.model.QuestionType;

public class QuestionRepositoryImpl implements QuestionRepository {

    private static final String INSERT_QUERY = "INSERT INTO questions(tag, text, type) VALUES(?,?,?)";
    private static final String SELECT_QUERY = "SELECT id, tag, text, type FROM questions ORDER BY id ASC LIMIT ? OFFSET ?";

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

    @Override
    public List<Question> loadPage(Connection conn, int offset, int limit) {
        try (PreparedStatement st = conn.prepareStatement(SELECT_QUERY)) {
            st.setInt(1, limit);
            st.setInt(2, offset);

            try (ResultSet rs = st.executeQuery()) {
                List<Question> questions = new ArrayList<>(limit);
                while (rs.next()) {
                    Question q = new Question()
                            .setId(rs.getLong(1))
                            .setTag(rs.getString(2))
                            .setText(rs.getString(3))
                            .setQuestionType(QuestionType.valueOf(rs.getString(4)));
                    questions.add(q);
                }
                return questions;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to load questions page", ex);
        }
    }
}
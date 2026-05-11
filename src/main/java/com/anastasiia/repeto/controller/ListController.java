package com.anastasiia.repeto.controller;

import java.util.List;

import com.anastasiia.repeto.model.Question;
import com.anastasiia.repeto.service.QuestionService;
import com.anastasiia.repeto.view.QuestionCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;

public class ListController {

    private int pageSize = 50;
    private int loadedCount = 0;
    private boolean isLoading = false;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ListView<Question> questionListView;

    private ObservableList<Question> questions = FXCollections.observableArrayList();

    private final QuestionService questionService;

    public ListController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @FXML
    public void initialize() {
        questionListView.setItems(questions);
        questionListView.setCellFactory(param -> new QuestionCell());

        scrollPane.vvalueProperty().addListener(((observableValue, number, t1) -> {
            if (number != null && !isLoading && t1.doubleValue() >= 0.9) {
                loadQuestions();
            }
        }));
        System.out.println("count: " + questions.size());

        loadQuestions();
    }

    @FXML
    public void loadQuestions() {
        if (isLoading) {
            return;
        }

        isLoading = true;

        new Thread(() -> {
            List<Question> batch = questionService.loadQuestions(loadedCount, pageSize);
            Platform.runLater(() -> {
                questions.addAll(batch);
                loadedCount += batch.size();
                isLoading = false;
            });
        }).start();
    }

}
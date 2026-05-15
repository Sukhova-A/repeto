package com.anastasiia.repeto.controller;

import java.util.List;

import com.anastasiia.repeto.model.Question;
import com.anastasiia.repeto.service.QuestionService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ListController {

    private int pageSize = 50;
    private int loadedCount = 0;
    private boolean isLoading = false;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TableView<Question> questionTableView;
    @FXML
    private TableColumn<Question, String> questionColumn;
    @FXML
    private TableColumn<Question, String> tagColumn;

    private ObservableList<Question> questions = FXCollections.observableArrayList();

    private final QuestionService questionService;

    public ListController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @FXML
    public void initialize() {
        questionTableView.setItems(questions);

        questionColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        questionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Question, String> call(TableColumn<Question, String> questionStringTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item);
                            setStyle("-fx-text-fill: #333; -fx-font-weight: bold; -fx-alignment: CENTER-LEFT");
                        }
                    }
                };
            }
        });

        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));
        tagColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Question, String> call(TableColumn<Question, String> questionStringTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item);
                            setStyle("-fx-text-fill: #666; -fx-font-size: 12px; -fx-background-color: #f0f0f0; "
                                    + "-fx-padding: 2px 8px; -fx-border-radius: 8px;");
                        }
                    }
                };
            }
        });

        questionColumn.setPrefWidth(500);
        tagColumn.setPrefWidth(100);
        tagColumn.setMaxWidth(150);
        tagColumn.setMinWidth(100);

        questionTableView.applyCss();
        questionTableView.layout();

        questionTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Platform.runLater(() -> {
            questionTableView.applyCss();
            questionTableView.layout();
            System.out.println("Final tag column width: " + tagColumn.getWidth());
        });

        scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !isLoading && newValue.doubleValue() >= 0.9) {
                loadQuestions();
            }
        });

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
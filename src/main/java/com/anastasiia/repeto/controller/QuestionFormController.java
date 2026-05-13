package com.anastasiia.repeto.controller;

import java.util.ArrayList;
import java.util.List;

import com.anastasiia.repeto.model.Answer;
import com.anastasiia.repeto.model.Question;
import com.anastasiia.repeto.model.QuestionType;
import com.anastasiia.repeto.service.QuestionService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class QuestionFormController {

    @FXML
    private TextField tagText;
    @FXML
    private TextArea questionText;
    @FXML
    private ComboBox<String> questionType;
    @FXML
    private VBox answersContainer;
    @FXML
    private Label errorLabel;

    private final QuestionService questionService;

    public QuestionFormController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @FXML
    public void initialize() {
        addAnswer();
        addAnswer();
        addAnswer();
        addAnswer();
    }

    @FXML
    public void addAnswer() {
        TextArea textArea = new TextArea();
        textArea.setPromptText("Введите ответ");
        textArea.setPrefRowCount(2);
        textArea.setWrapText(true);

        CheckBox isCorrect = new CheckBox("Верный");
        isCorrect.setPrefWidth(80);
        isCorrect.setMaxWidth(80);
        isCorrect.setMinWidth(80);
        Button deleteButton = new Button("X");
        deleteButton.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white");
        deleteButton.setOnAction(event -> {
            HBox parentBox = (HBox) deleteButton.getParent();
            answersContainer.getChildren().remove(parentBox);
        });

        HBox answerBox = new HBox(10, textArea, isCorrect, deleteButton);
        HBox.setHgrow(textArea, Priority.ALWAYS);
        answersContainer.getChildren().add(answerBox);
    }

    @FXML
    public void saveQuestion() {
        //todo сделать проверку на уже существующий вопрос
        if (answersContainer.getChildren().size() < 4) {
            errorLabel.setText("Нужно хотя бы 4 ответа!");
            errorLabel.setVisible(true);
            return;
        }

        long selectedCount = answersContainer.getChildren().stream()
                .map(hBox -> (CheckBox) ((HBox) hBox).getChildren().get(1))
                .filter(CheckBox::isSelected)
                .count();

        if (selectedCount == 0) {
            errorLabel.setText("Нужен хотя бы 1 верный ответ!");
            errorLabel.setVisible(true);
            return;
        }

        if (questionType.getValue().equals("Множественный выбор")
                && answersContainer.getChildren().size() == selectedCount) {
            errorLabel.setText("Все ответы отмечены верными.");
            errorLabel.setVisible(true);
            return;
        }

        Question question = new Question()
                .setText(questionText.getText())
                .setTag(tagText.getText())
                .setQuestionType(questionType.getValue().equals("Одиночный выбор")
                        ? QuestionType.SINGLE_CHOICE : QuestionType.MULTIPLE_CHOICE);

        List<Answer> answers = new ArrayList<>();
        List<HBox> hBoxes = answersContainer.getChildren().stream()
                .map(it -> (HBox) it)
                .toList();
        for (HBox hBox : hBoxes) {
            TextArea text = (TextArea) hBox.getChildren().get(0);
            if (text.getText().isBlank()) {
                errorLabel.setText("Ответы не могут быть пустыми!");
                errorLabel.setVisible(true);
                return;
            }
            CheckBox checkBox = (CheckBox) hBox.getChildren().get(1);
            Answer answer = new Answer()
                    .setAnswer(text.getText())
                    .setCorrect(checkBox.isSelected());

            answers.add(answer);
        }

        boolean success = questionService.createQuestionWIthAnswers(question, answers);
        System.out.println("Question saved: " + success);

        resetForm();
    }

    public void resetForm() {
        questionText.clear();
        questionType.getSelectionModel().clearSelection();

        answersContainer.getChildren().clear();
        errorLabel.setText("");

        this.initialize();
    }
}
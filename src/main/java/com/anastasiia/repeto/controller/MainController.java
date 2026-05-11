package com.anastasiia.repeto.controller;

import java.io.IOException;

import com.anastasiia.repeto.repository.AnswerRepositoryImpl;
import com.anastasiia.repeto.repository.QuestionRepositoryImpl;
import com.anastasiia.repeto.service.QuestionService;
import com.anastasiia.repeto.service.ValidatorService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private void openQuestionForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/anastasiia/repeto/QuestionForm.fxml"));
        loader.setControllerFactory(param -> {
            if (param == QuestionFormController.class) {
                return new QuestionFormController(
                        new QuestionService(
                                new QuestionRepositoryImpl(),
                                new AnswerRepositoryImpl(),
                                new ValidatorService()
                        )
                );
            }
            return null;
        });

        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Добавить вопрос");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.NONE);
        stage.setResizable(true);
        stage.showAndWait();
    }

    @FXML
    private void startTest() {

    }

    @FXML
    private void showQuestionList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/anastasiia/repeto/ListForm.fxml"));
        loader.setControllerFactory(param -> {
            if (param == ListController.class) {
                return new ListController(
                        new QuestionService(
                                new QuestionRepositoryImpl(),
                                new AnswerRepositoryImpl(),
                                new ValidatorService()
                        )
                );
            }
            return null;
        });

        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Список вопросов");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.NONE);
        stage.setResizable(true);
        stage.showAndWait();
    }
}
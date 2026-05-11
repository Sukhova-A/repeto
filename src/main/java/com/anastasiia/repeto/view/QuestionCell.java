package com.anastasiia.repeto.view;

import com.anastasiia.repeto.controller.ListController;
import com.anastasiia.repeto.model.Question;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class QuestionCell extends ListCell<Question> {

    private HBox container;
    private TextField textField;
    private TextField categoryField;

    public QuestionCell() {
        container = new HBox(10);
        container.setPadding(new Insets(5));

        textField = new TextField();
        textField.setPrefWidth(300);

        categoryField = new TextField();
        categoryField.setPrefWidth(100);

        container.getChildren().addAll(textField, categoryField);
    }

    @Override
    protected void updateItem(Question item, boolean empty) {
        super.updateItem(item, empty);
        System.out.println("Cell debug");

        if (empty || item == null) {
            setGraphic(null);
            setText(null);
        } else {
            textField.setText(item.getText());
            categoryField.setText(item.getTag());

            setGraphic(container);
        }
    }
}

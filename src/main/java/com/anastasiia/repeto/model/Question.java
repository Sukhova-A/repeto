package com.anastasiia.repeto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Question {
    private Long id;
    private String tag;
    private String text;
    private QuestionType questionType;
}
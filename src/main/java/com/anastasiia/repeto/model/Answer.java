package com.anastasiia.repeto.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Answer {

    private Long id;
    private Long questionId;
    private String answer;
    private boolean isCorrect;
}

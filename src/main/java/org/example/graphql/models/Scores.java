package org.example.graphql.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scores {
    private String kind;
    private int maxScoresAmount;
    private PerQuestion perQuestion;
    private Object partialAnswers;
}


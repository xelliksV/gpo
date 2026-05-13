package org.example.graphql.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerWrite {
    private String uid;
    private String index;
    private List<String> options;
    private String commentCorrect;
    private Object commentCorrectProse;
    private String commentIncorrect;
    private Object commentIncorrectProse;
    private Object rewards;
}


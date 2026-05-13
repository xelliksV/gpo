package org.example.graphql.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String uid;
    private String processNote;
    private String question;
    private ProsaContent questionProse;
    private Scores scores;
    private Object extraRewards;
    private String kind;
    private List<String> answerParts;
    private Object answersSingleFromTwo;
    private Object answersSingleFromMultiple;
    private Object answersMultipleFromMultiple;
    private Object answersMatchPairs;
    private List<AnswerWrite> answersWrite;
    private Object answersFillBlanks;
    private Object answersDistributeByGroups;
    private Object answersWordsSequence;
    private String commentCorrect;
    private ProsaContent commentCorrectProse;
    private String commentIncorrect;
    private ProsaContent commentIncorrectProse;
    private Object dialogSimulator;
    private Object dragAndDropToArea;
    private Object selectArea;
    private Object interactiveVideo;
}

